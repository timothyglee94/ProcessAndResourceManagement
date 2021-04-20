
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


class ProcessControlBlock
{
    public int pn;
    public int state;
    public int parent;
    public LinkedList<Integer> children;
    public LinkedList<Integer> resources;
    public LinkedList<Integer> resource_amount;
    public int priority;

    public ProcessControlBlock()
    {
        pn = 0;
        state = 1;
        parent = -1;
        children = new LinkedList<Integer>();
        resources =  new LinkedList<Integer>();
        resource_amount =  new LinkedList<Integer>();
        priority = 0;
    }
    public ProcessControlBlock(int i, int p, int n)
    {
        pn = n;
        state = 1;
        parent = i;
        children = new LinkedList<Integer>();
        resources =  new LinkedList<Integer>();
        resource_amount =  new LinkedList<Integer>();
        priority = p;
    }
    public void set_state(int i)
    {
        this.state = i;
    }
    public void set_parent(int i)
    {
        this.parent = i;
    }
    public void add_child(int i)
    {
        this.children.add(i);
    }
    public void add_resource(int i, int amount)
    {
        if(this.resources.indexOf(i) > -1)
        {
            this.resource_amount.set(this.resources.indexOf(i), this.resource_amount.get(this.resources.indexOf(i))+ amount);
        }
        else
        {
            this.resources.add(i);
            this.resource_amount.add(amount);
        }
    }
    public boolean has_resource()
    {
        if(this.resources.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public int get_resource()
    {
        return this.resources.getFirst();
    }
    public int get_resource_amount()
    {
        return this.resource_amount.getFirst();
    }
    public int get_state()
    {
        return this.state;
    }
    public int get_parent()
    {
        return this.parent;
    }
    public int get_priority(){return this.priority;}
    public int get_process_number(){return this.pn;}
    public void remove_child(int i)
    {
        this.children.remove(this.children.indexOf(i));
    }
    public LinkedList<Integer> get_children_list()
    {
        return this.children;
    }
    public LinkedList<Integer> get_resource_list()
    {
        return this.resources;
    }
    public LinkedList<Integer> get_amount_list()
    {
        return this.resource_amount;
    }

    public int get_resource_amount(int i)
    {

        return this.resource_amount.get(this.resources.indexOf(i));
    }
    public void remove_resource()
    {
        this.resources.removeFirst();
        this.resource_amount.removeFirst();
    }
}

class ResourceControlBlock
{
    public int state;
    LinkedList<Integer> waitlist;
    LinkedList<Integer> rqunit;
    public int inventory;
    public ResourceControlBlock(int i)
    {
        waitlist = new LinkedList<Integer>();
        rqunit = new LinkedList<Integer>();
        if(i == 0 || i == 1)
        {
            inventory = 1;
            state = 1;
        }
        else
        {
            inventory = i;
            state = i;
        }

    }
    public void set_state(int i)
    {
        this.state = i;
    }
    public void add_waitlist(int i, int r)
    {
        this.waitlist.add(i);
        this.rqunit.add(r);
    }
    public void remove_waitlist(int i)
    {
        this.rqunit.remove(this.waitlist.indexOf(i));
        this.waitlist.remove(this.waitlist.indexOf(i));
    }
    public int get_head()
    {
        return this.waitlist.getFirst();
    }
    public int get_head_rq()
    {
        return this.rqunit.getFirst();
    }
    public boolean free_of_waitlist()
    {
        if(this.waitlist.size() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean in_waitlist(int i)
    {
        if(this.waitlist.indexOf(i)> -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public int get_state()
    {
        return this.state;
    }
    public void remove_first_waitlist()
    {
        this.waitlist.removeFirst();
        this.rqunit.removeFirst();
    }
    public void free_all()
    {
        this.state = inventory;
    }
    public LinkedList<Integer> getWaitlist()
    {
        return this.waitlist;
    }
    public LinkedList<Integer> getRqunit()
    {
        return this.rqunit;
    }

}

class ReadyList
{
    public LinkedList<ProcessControlBlock> pr0;
    public LinkedList<ProcessControlBlock> pr1;
    public LinkedList<ProcessControlBlock> pr2;

    public ReadyList()
    {

        pr0 = new LinkedList<ProcessControlBlock>();
        pr1 = new LinkedList<ProcessControlBlock>();
        pr2 = new LinkedList<ProcessControlBlock>();

    }
    public boolean in_RL(ProcessControlBlock i)
    {
        if(i.get_priority() == 0)
        {
            return this.pr0.contains(i);
        }
        else if(i.get_priority() == 1)
        {
            return this.pr1.contains(i);
        }
        else if(i.get_priority() == 2)
        {
            return this.pr2.contains(i);
        }
        else
        {
            return false;
        }
    }
    public void add_readylist(ProcessControlBlock i)
    {
        if(i.get_priority() == 0)
        {
            this.pr0.add(i);
        }
        else if(i.get_priority() == 1)
        {
            this.pr1.add(i);
        }
        else if(i.get_priority() == 2)
        {
            this.pr2.add(i);
        }
    }
    public void remove_readylist(ProcessControlBlock i)
    {
        if(i.get_priority() == 0)
        {
            this.pr0.remove(i);
        }
        if(i.get_priority() == 1)
        {
            this.pr1.remove(i);
        }
        if(i.get_priority() == 2)
        {
            this.pr2.remove(i);
        }
    }
    public ProcessControlBlock get_current()
    {
        if(this.pr2.size() > 0)
        {
            return this.pr2.getFirst();
        }
        else if(this.pr1.size() > 0)
        {
            return this.pr1.getFirst();
        }
        else
        {
            return this.pr0.getFirst();
        }
    }
    public void remove_current()
    {
        if(this.pr2.size() > 0)
        {
            this.pr2.removeFirst();
        }
        else if(this.pr1.size() > 0)
        {
            this.pr1.removeFirst();
        }
        else
        {
            this.pr0.removeFirst();
        }
    }
    public void timeout()
    {
        if(this.pr2.size() > 0)
        {
            this.pr2.add(this.pr2.getFirst());
            this.pr2.removeFirst();
        }
        else if(this.pr1.size() > 0)
        {
            this.pr1.add(this.pr1.getFirst());
            this.pr1.removeFirst();
        }
        else
        {
            this.pr0.add(this.pr0.getFirst());
            this.pr0.removeFirst();
        }
    }
    public int scheduler()
    {
        if(this.pr2.size() > 0)
        {
            ProcessControlBlock highest_p = this.pr2.getFirst();
            int highest = highest_p.get_priority();
            int j_index = 0;

            for(int i = 0; i < this.pr2.size(); i++)
            {
                if(highest_p.get_priority() < this.pr2.get(i).get_priority())
                {
                    highest_p = this.pr2.get(i);
                    j_index = i;
                }
            }
            if(j_index != 0)
            {
                this.pr2.remove(j_index);
                this.pr2.add(j_index, this.get_current());
                this.pr2.removeFirst();
                this.pr2.addFirst(highest_p);
            }
            return highest_p.get_process_number();
        }
        else if(this.pr1.size() > 0)
        {
            ProcessControlBlock highest_p = this.pr1.getFirst();
            int highest = highest_p.get_priority();
            int j_index = 0;

            for(int i = 0; i < this.pr1.size(); i++)
            {
                if(highest_p.get_priority() < this.pr1.get(i).get_priority())
                {
                    highest_p = this.pr1.get(i);
                    j_index = i;
                }
            }
            if(j_index != 0)
            {
                this.pr1.remove(j_index);
                this.pr1.add(j_index, this.get_current());
                this.pr1.removeFirst();
                this.pr1.addFirst(highest_p);
            }
            return highest_p.get_process_number();
        }
        else
        {
            ProcessControlBlock highest_p = this.pr0.getFirst();
            int highest = highest_p.get_priority();
            int j_index = 0;

            for(int i = 0; i < this.pr0.size(); i++)
            {
                if(highest_p.get_priority() < this.pr0.get(i).get_priority())
                {
                    highest_p = this.pr0.get(i);
                    j_index = i;
                }
            }
            if(j_index != 0) {
                this.pr0.remove(j_index);
                this.pr0.add(j_index, this.get_current());
                this.pr0.removeFirst();
                this.pr0.addFirst(highest_p);
            }
            return highest_p.get_process_number();
        }

    }
    public void get_current_list()
    {

        System.out.print("current ready list is ");

        if(this.pr2.size() > 0)
        {
            System.out.print("PR2: ");
            for(int i = 0; i < this.pr2.size(); i++)
            {
                System.out.print(this.pr2.get(i).get_process_number() +" ");
            }
        }
        if(this.pr1.size() > 0)
        {
            System.out.print("PR1: ");
            for(int i = 0; i < this.pr1.size(); i++)
            {
                System.out.print(this.pr1.get(i).get_process_number() +" ");
            }
        }
        if(this.pr0.size() > 0)
        {
            System.out.print("PR0: ");
            for(int i = 0; i < this.pr0.size(); i++)
            {
                System.out.print(this.pr0.get(i).get_process_number() +" ");
            }
        }
        System.out.println("");
    }
}

public class PRM {

    public static void main(String[] args) {

        int MAX_PROCESS = 16;
        int MAX_RESOURCE = 4;
        ArrayList<Integer> available = new ArrayList<Integer>();
        for(int i = 1; i<MAX_PROCESS; i++)
        {
            available.add(i);
        }

        ReadyList RL = new ReadyList();
        ProcessControlBlock PCB[] = new ProcessControlBlock[MAX_PROCESS];
        ResourceControlBlock RCB[] = new ResourceControlBlock[MAX_RESOURCE];
        ProcessControlBlock fp = new ProcessControlBlock();
        PCB[0] = fp;
        RL.add_readylist(fp);

        for (int i = 0; i < MAX_RESOURCE; i++)
        {
            ResourceControlBlock rb = new ResourceControlBlock(i);
            RCB[i] = rb;
        }

        try
        {
            File file = new File("input.txt");
            Scanner scan = new Scanner(file);
            PrintWriter output = new PrintWriter("output.txt", "UTF-8");
            int count = 0;
            while(scan.hasNext()){

                String input = scan.next();


                if(input.equals("in")) {

                    available = new ArrayList<Integer>();
                    for(int i = 1; i<MAX_PROCESS; i++)
                    {
                        available.add(i);
                    }
                    RL = new ReadyList();
                    PCB = new ProcessControlBlock[MAX_PROCESS];
                    RCB = new ResourceControlBlock[MAX_RESOURCE];
                    fp = new ProcessControlBlock();
                    PCB[0] = fp;
                    RL.add_readylist(fp);

                    for (int i = 0; i < MAX_RESOURCE; i++)
                    {
                        ResourceControlBlock rb = new ResourceControlBlock(i);
                        RCB[i] = rb;
                    }
                    if(count != 0) {
                        output.println("");
                    }
                    output.print(RL.scheduler() + " ");
                }

                else if(input.equals("cr"))
                {
                    int priority = Integer.parseInt(scan.next());
                    if(priority < 0 || priority > 2)
                    {
                        output.print("-1");
                    }
                    else
                    {
                        if(available.size() > 0)
                        {
                            int parent_number = RL.get_current().get_process_number();
                            ProcessControlBlock child = new ProcessControlBlock(parent_number, priority, available.get(0));
                            PCB[parent_number].add_child(available.get(0));
                            PCB[available.get(0)] = child;
                            RL.add_readylist(child);
                            available.remove(0);
                            output.print(RL.scheduler() + " ");

                        }
                        else
                        {
                            output.print("-1");
                        }
                    }
                }

                else if(input.equals("de"))
                {
                    int index = Integer.parseInt(scan.next());

                    LinkedList<Integer> Childlist = PCB[RL.get_current().get_process_number()].get_children_list();
                    if(Childlist.indexOf(index) > -1)
                    {
                        destory(RCB, PCB, RL, index, available, RL.get_current().get_process_number());
                        output.print(RL.scheduler() + " ");
                    }
                    else
                    {
                        output.print("-1");
                    }

                    Collections.sort(available);
                }

                else if(input.equals("rq"))
                {
                    int index = Integer.parseInt(scan.next());
                    int rqunit = Integer.parseInt(scan.next());

                    if (RL.get_current().get_process_number() > 0)
                    {
                        if (index >= 0 && index < 4)
                        {
                            if(rqunit > RCB[index].inventory)
                            {
                                output.print("-1");
                            }
                            else
                            {

                                int avail_unit = RCB[index].state;
                                if(rqunit > avail_unit)
                                {
                                    PCB[RL.get_current().get_process_number()].set_state(0);
                                    RCB[index].add_waitlist(RL.get_current().get_process_number(), rqunit);
                                    RL.remove_current();
                                }
                                else
                                {
                                    RCB[index].set_state(avail_unit - rqunit);
                                    PCB[RL.get_current().get_process_number()].add_resource(index, rqunit);
                                }
                                output.print(RL.scheduler() + " ");


                            }

                        }
                        else
                        {
                            output.print("-1");
                        }
                    }
                    else
                    {
                        output.print("-1");
                    }

                }
                else if(input.equals("rl"))
                {
                    int index = Integer.parseInt(scan.next());
                    int rlunit = Integer.parseInt(scan.next());

                    if(index >= 0 && index < 4)
                    {
                        if(rlunit > 0 && rlunit < 4 )
                        {
                            if (index == 0 && rlunit > 1)
                            {
                                output.print("-1");
                            }
                            else if (index == 1 && rlunit > 1) {

                                output.print("-1");
                            }
                            else if (index == 2 && rlunit > 2)
                            {
                                output.print("-1");
                            }
                            else if (index == 3 && rlunit > 3)
                            {
                                output.print("-1");
                            }
                            else
                            {
                                LinkedList<Integer> resources = PCB[RL.get_current().get_process_number()].get_resource_list();
                                if (resources.indexOf(index) > -1)
                                {
                                    if(rlunit > PCB[RL.get_current().get_process_number()].get_resource_amount(index))
                                    {
                                        output.print("-1");
                                    }
                                    else
                                    {
                                        waitlist(RCB, PCB, RL, index, rlunit);
                                        output.print(RL.scheduler() + " ");
                                    }
                                }
                                else
                                {
                                    output.print("-1");
                                }
                            }

                        }
                        else
                        {
                            output.print("-1");
                        }
                    }
                    else
                    {
                        output.print("-1");
                    }
                }
                else if(input.equals("to"))
                {
                    RL.timeout();
                    output.print(RL.scheduler() + " ");
                }

                else
                {
                    output.print("-1");
                }
                count ++;
            }

            scan.close();
            output.close();
        }
        catch(IOException ex){
            System.err.println("file not found");
        }
    }


    static void destory(ResourceControlBlock RCB[], ProcessControlBlock PCB[], ReadyList RL, int index, ArrayList<Integer> available, int current)
    {
        LinkedList<Integer> children = PCB[index].get_children_list();
        if(children.size() < 1)
        {
            PCB[current].remove_child(index);
            available.add(index);
            if(RL.in_RL(PCB[index]))
            {
                RL.remove_readylist(PCB[index]);
            }
            if (PCB[index].has_resource())
            {
                for (int i = 0; i < (PCB[index].get_resource_list()).size(); i++)
                {
                    int resource_num = PCB[index].get_resource();
                    int resource_amount = PCB[index].get_resource_amount();
                    waitlist(RCB, PCB, RL, resource_num, resource_amount);
                    PCB[index].remove_resource();
                }
            }
            for (int i = 0; i < 4; i++)
            {
                if (RCB[i].in_waitlist(index))
                {
                    RCB[i].remove_waitlist(index);
                }
            }
        }
        else
        {
            for (int i = 0; i < children.size(); i++)
            {
                destory(RCB, PCB, RL, children.getFirst(), available, index);
            }
            destory(RCB,PCB,RL,index,available,current);
        }
    }

    static void waitlist(ResourceControlBlock RCB[], ProcessControlBlock PCB[], ReadyList RL, int index, int amount)
    {
        RCB[index].set_state(RCB[index].get_state() + amount);
        int avail_resource = RCB[index].get_state();

        while (avail_resource > 0)
        {
            if(!RCB[index].free_of_waitlist())
            {
                if (RCB[index].get_head_rq() <= avail_resource)
                {
                    PCB[RCB[index].get_head()].set_state(1);
                    PCB[RCB[index].get_head()].add_resource(index, RCB[index].get_head_rq());
                    RL.add_readylist(PCB[RCB[index].get_head()]);
                    PCB[index].set_state(avail_resource - RCB[index].get_head_rq());
                    RCB[index].remove_first_waitlist();
                    avail_resource = RCB[index].get_state();
                }
                else
                {
                    avail_resource = 0;
                }
            }
            else
            {
                RCB[index].free_all();
                avail_resource = 0;
            }
        }
    }


}


