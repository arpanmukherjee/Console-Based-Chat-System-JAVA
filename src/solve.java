import java.util.*;
import java.io.*;
public class solve
{
    static List<Admin> adminList = new ArrayList<Admin>();
    static List<User> userList = new ArrayList<User>();
    static List<Group> groupList = new ArrayList<Group>();

    static Boolean adminValid(String name)
    {
        int i;
        for(i=0;i<adminList.size();i++)
        {
            if(adminList.get(i).getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    static Boolean userValid(String name)
    {
        int i;
        for(i=0;i<userList.size();i++)
        {
            if(userList.get(i).getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    static Boolean phoneValid(String phone)
    {
        if(phone.length()!=10)
        {
            System.out.println("Phone No has to be of length 10!");
            return false;
        }
        else
        {
            int i;
            for (i=0;i<10;i++)
            {
                if(phone.charAt(i)>='0' && phone.charAt(i)<='9')
                    continue;
                else
                {
                    System.out.println("Phone no should contain only digits!");
                    return false;
                }
            }
        }
        return true;
    }

    static Boolean groupValid(String name)
    {
        int i;
        for(i=0;i<groupList.size();i++)
        {
            if(groupList.get(i).getGroupName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    static int getGroupIndex(String name)
    {
        int i,pos;
        pos=-1;
        for(i=0;i<groupList.size();i++)
        {
            if(groupList.get(i).getGroupName().equalsIgnoreCase(name))
            {
                pos=i;
                break;
            }
        }
        return pos;
    }

    static int getUserIndex(String name)
    {
        int i,pos;
        pos=-1;
        for(i=0;i<userList.size();i++)
        {
            if(userList.get(i).getName().equalsIgnoreCase(name))
            {
                pos=i;
                break;
            }
        }
        return pos;
    }

    static int getAdminIndex(String name)
    {
        int i,pos;
        pos=-1;
        for(i=0;i<adminList.size();i++)
        {
            if(adminList.get(i).getName().equalsIgnoreCase(name))
            {
                pos=i;
                break;
            }
        }
        return pos;
    }

    public static void main(String args[])
    {
        int i,pos;
        try
        {
            Scanner sc=new Scanner(new File("input.txt"));
            String input;
            while (sc.hasNextLine())
            {
                System.out.println();
                input = sc.nextLine();
                String[] tokens = input.split(" ");
                if(tokens[0].equalsIgnoreCase("ADDA"))
                {
                    if(!adminValid(tokens[1]))
                    {
                        Admin aobj = new Admin();
                        aobj.setName(tokens[1]);
                        adminList.add(aobj);
                        System.out.println("Admin Successfully added!");
                    }
                    else
                        System.out.println("Admin name already exists! It has to be unique!");
                }

                else if(tokens[0].equalsIgnoreCase("ADDU"))
                {
                    if(!userValid(tokens[1]))
                    {
                        if(phoneValid(tokens[2])) {
                            User uobj = new User();
                            uobj.setName(tokens[1]);
                            uobj.setPhoneNo(tokens[2]);
                            uobj.setAddr(tokens[3]);
                            userList.add(uobj);
                            System.out.println("User Successfully added!");
                        }
                    }
                    else
                        System.out.println("Username already exists! It has to be unique!");
                }

                else if(tokens[0].equalsIgnoreCase("CREATEG"))
                {
                    if(!groupValid(tokens[1])) {
                        Group gobj = new Group();
                        gobj.setGroupName(tokens[1]);
                        gobj.setAdminName(tokens[2]);
                        groupList.add(gobj);
                        System.out.println("Group Successfully craeted!");
                    }
                    else
                        System.out.println("Group Name already exists! It has to be unique!");
                }

                else if(tokens[0].equalsIgnoreCase("REMOVEG"))
                {
                    if (groupValid(tokens[1]))
                    {
                        pos=getGroupIndex(tokens[1]);
                        if(groupList.get(pos).getAdminName().equalsIgnoreCase(tokens[2]))
                        {
                            groupList.remove(pos);
                            System.out.println("Group has been successfully removed!");
                        }
                        else
                        {
                            System.out.println("Sorry! Admin name isn't correct for the group!");
                            continue;
                        }
                    }
                    else
                        System.out.println("Sorry,group doesn't exist!");
                }

                else if(tokens[0].equalsIgnoreCase("VIEWM"))
                {
                    if(groupValid(tokens[2]))
                    {
                        pos = getGroupIndex(tokens[2]);
                        if(userValid(tokens[1]))
                        {
                            if(groupList.get(pos).isMember(tokens[1]))
                            {
                                groupList.get(pos).viewMessage();
                                continue;
                            }
                            else
                            {
                                System.out.println("Sorry,user isn't member of the group!");
                                continue;
                            }
                        }
                        else
                        {
                            if(groupList.get(pos).getAdminName().equalsIgnoreCase(tokens[1]))
                            {
                                groupList.get(pos).viewMessage();
                                continue;
                            }
                            System.out.println("Sorry! Invalid username!");
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("Sorry! Invalid group name!");
                        continue;
                    }
                }

                else if(tokens[0].equalsIgnoreCase("SENDM"))
                {
                    if(userValid(tokens[1]) || adminValid(tokens[1]))
                    {
                        if(groupValid(tokens[2]))
                        {
                            Message mobj = new Message();
                            mobj.setUser(tokens[1]);
                            mobj.setGroup(tokens[2]);
                            mobj.setMessage(tokens[3]);
                            groupList.get(getGroupIndex(tokens[2])).addMessage(mobj);
                            System.out.println("The message was successfully sent to the group!");
                            continue;
                        }
                        else
                        {
                            System.out.println("Group Name is invalid!");
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("User name isn't valid!");
                        continue;
                    }
                }

                else if(tokens[0].equalsIgnoreCase("REQU"))
                {
                    if(userValid(tokens[1]))
                    {
                        if(groupValid(tokens[2]))
                        {
                            pos = getGroupIndex(tokens[2]);
                            groupList.get(pos).requestUser(userList.get(getUserIndex(tokens[1])));
                            continue;
                        }
                        else {
                            System.out.println("Group name isn't valid!");
                            continue;
                        }
                    }
                    else {
                        System.out.println("User name isn't valid!");
                        continue;
                    }
                }

                else if(tokens[0].equalsIgnoreCase("LEAVE"))
                {
                    if(userValid(tokens[1]))
                    {
                        if(groupValid(tokens[2]))
                        {
                            pos = getGroupIndex(tokens[2]);
                            if(groupList.get(pos).isMember(tokens[1]))
                            {
                                groupList.get(pos).removeUser(userList.get(getUserIndex(tokens[1])));
                                continue;
                            }
                        }
                        else {
                            System.out.println("Group name isn't valid!");
                            continue;
                        }
                    }
                    else {
                        System.out.println("User name isn't valid!");
                        continue;
                    }
                }

                else if(tokens[0].equalsIgnoreCase("PENDING"))
                {
                    if(groupValid(tokens[1]))
                    {
                        pos = getGroupIndex(tokens[1]);
                        if(groupList.get(pos).pendingUser.size()==0)
                        {
                            System.out.println("NO Pending request for this group!");
                            continue;
                        }
                        else
                        {
                            System.out.println("Usernames of pending requests:");
                            for (int j = 0; j < groupList.get(pos).pendingUser.size(); j++) {
                                System.out.println(groupList.get(pos).pendingUser.get(j).getName());
                            }
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("Group Name isn't valid!");
                        continue;
                    }
                }

                else if(tokens[0].equalsIgnoreCase("ACCEPT"))
                {
                    if(adminValid(tokens[1]))
                    {
                        if(groupValid(tokens[2]))
                        {
                            if(userValid(tokens[3]))
                            {
                                pos = getGroupIndex(tokens[2]);
                                if(groupList.get(pos).getAdminName().equalsIgnoreCase(tokens[1])) {
                                    groupList.get(pos).addUser(userList.get(getUserIndex(tokens[3])),tokens[4]);
                                }
                                else {
                                    System.out.println("admin name has to be admin of the group!");
                                    continue;
                                }
                            }
                            else
                            {
                                System.out.println("User Name isn't valid!");
                                continue;
                            }
                        }
                        else
                        {
                            System.out.println("Group name isn't valid!");
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("Invalid admin name!");
                        continue;
                    }
                }

                else if(tokens[0].equalsIgnoreCase("SHARE"))
                {
                    Group g1,g2;
                    if(groupValid(tokens[2]) && groupValid(tokens[3]))
                    {
                        g1 = groupList.get(getGroupIndex(tokens[2]));
                        g2 = groupList.get(getGroupIndex(tokens[3]));
                        if(g1.isMember(tokens[1]) && g2.isMember(tokens[1]))
                        {
                            pos = Integer.parseInt(tokens[4]);
                            if(g1.messageList.size()>=pos)
                            {
                                g2.addMessage(g1.messageList.get(pos - 1));
                                System.out.println("Message was successfully shared to the group!");
                                continue;
                            }
                            else
                            {
                                System.out.println("Please enter valid Message ID!");
                                continue;
                            }
                        }
                        else
                        {
                            System.out.println("User must be member of both the groups to share the message!");
                            continue;
                        }
                    }
                    else
                        System.out.println("Group Names are not valid!");
                }

                else if(tokens[0].equalsIgnoreCase("REPLY"))
                {
                    if(groupValid(tokens[2]))
                    {
                        pos = getGroupIndex(tokens[2]);
                        if((userValid(tokens[1]) && groupList.get(pos).isMember(tokens[1])) ||
                                groupList.get(pos).getAdminName().equalsIgnoreCase(tokens[1]))
                        {
                            Reply robj = new Reply();
                            robj.setUser(tokens[1]);
                            robj.setGroupName(tokens[2]);
                            robj.setMessage(tokens[3]);
                            robj.setmId(Integer.parseInt(tokens[4]));
                            groupList.get(pos).addReply(robj);
                            System.out.println("Reply was sent successfully to the group!");
                        }
                        else
                        {
                            System.out.println("Either username is invalid or user isn't member of the group!");
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("Group name isn't valid!");
                        continue;
                    }
                }

                else if(tokens[0].equalsIgnoreCase("VIEWR"))
                {
                    if(groupValid(tokens[2]))
                    {
                        pos = getGroupIndex(tokens[2]);
                        if(userValid(tokens[1]))
                        {
                            if(groupList.get(pos).isMember(tokens[1]))
                            {
                                groupList.get(pos).viewReply();
                                continue;
                            }
                            else
                            {
                                System.out.println("Sorry,user isn't member of the group!");
                                continue;
                            }
                        }
                        else
                        {
                            if(groupList.get(pos).getAdminName().equalsIgnoreCase(tokens[1]))
                            {
                                groupList.get(pos).viewReply();
                                continue;
                            }
                            System.out.println("Sorry! Invalid username!");
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("Sorry! Invalid group name!");
                        continue;
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception occured!"+e);
        }
    }
}
