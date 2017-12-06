import java.util.ArrayList;
import java.util.List;

public class Admin extends Entity
{
    List<Group> groupList = new ArrayList<>();

    public void addGroup(Group gobj)
    {
        groupList.add(gobj);
    }

    public void removeGroup(Group gobj)
    {
        if(groupList.contains(gobj))
        {
            groupList.remove(gobj);
        }
    }

}
