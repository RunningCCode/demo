package test;

/**
 * @author chenzhicong
 * @time 2019/5/11 14:38
 * @description
 */
public class BUG {
    public GirlFriend howToDo() throws Exception{
        GirlFriend girlFriend = this.girlFriend;
        try{
            if(girlFriend.isUnHappy()){
                throw new BigException();
            }
        }catch (BigException e1){
            do{
                try{
                    makeGirlFriendHappy(girlFriend);
                }catch (BigBigException e2){
                    return howToDo();
                }
            }while (girlFriend.isUnHappy());
        }catch (NullPointerException e3){
            Thread.sleep(Long.MAX_VALUE);
        }
        return girlFriend;
    }




    public void makeGirlFriendHappy(GirlFriend gf){
        return;
    }
    GirlFriend girlFriend = null;
}
