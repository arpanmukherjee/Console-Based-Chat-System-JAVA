import java.util.ArrayList;
import java.util.List;

public class Group
{
    String groupName,adminName;
    List<User> userList = new ArrayList<User>();
    List<Message> messageList = new ArrayList<Message>();
    List<User> pendingUser = new ArrayList<User>();
    List<Reply> replyList = new ArrayList<Reply>();

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean isMember(String _name)
    {
        int i;
        for(i=0;i<userList.size();i++)
        {
            if(userList.get(i).getName().equalsIgnoreCase(_name))
                return true;
        }
        return false;
    }

    public void requestUser(User uobj)
    {
        if(userList.contains(uobj))
        {
            System.out.println("User already a member of the group!");
        }
        else if(pendingUser.contains(uobj))
        {
            System.out.println("User already has a pending request!");
        }
        else
        {
            pendingUser.add(uobj);
            System.out.println("User requested successfully! Waiting for the admin ");
        }
    }

    public void addUser(User uobj,String status)
    {
        if(userList.contains(uobj))
        {
            System.out.println("User already member of the group!");
        }
        else if(pendingUser.contains(uobj))
        {
            pendingUser.remove(uobj);
            if(status.equalsIgnoreCase("yes")) {
                userList.add(uobj);
                System.out.println("User added to the group successfully!");
            }
            else {
                System.out.println("User request rejected successfully!");
            }
        }
        else {
            System.out.println("User hasn't requested to the group yet!");
        }
    }

    public void removeUser(User uobj)
    {
        if(userList.contains(uobj))
        {
            userList.remove(uobj);
            System.out.println("Member was successfully removed from the group!");
        }
        else
        {
            System.out.println("Sorry! User isn't member of the group!");
        }
    }

    public void viewUser()
    {
        System.out.println("Group Members of the group:");
        int i;
        for(i=0;i<userList.size();i++)
        {
            System.out.println();
        }
    }

    public void addMessage(Message mobj)
    {
        messageList.add(mobj);
    }

    public  void viewMessage()
    {
        if(messageList.size()==0)
        {
            System.out.println("Sorry! There are no messages in the group!");
            return;
        }
        System.out.println("Conversation of the group-");
        for (int j = 0; j < messageList.size(); j++)
        {
            System.out.print((j+1)+": ");
            if(messageList.get(j).getGroup().equalsIgnoreCase(this.groupName))
                System.out.println(messageList.get(j).getUser() + " : " + messageList.get(j).getMessage());
            else
                System.out.println(messageList.get(j).getUser() + " in " + messageList.get(j).getGroup()+" : " + messageList.get(j).getMessage());
        }
    }

    public void addReply(Reply robj)
    {
        replyList.add(robj);
    }

    public void viewReply()
    {
        if(replyList.size()==0)
        {
            System.out.println("Sorry,this group doesn't contain any reply!");
            return;
        }
        System.out.println("Replies of the group:");
        for (int i = 0; i < replyList.size(); i++)
        {
            System.out.println("Reply to message Id:"+replyList.get(i).getmId()+" by the user "+replyList.get(i).getUser()+" message is: "+replyList.get(i).getMessage());
        }
    }
}
