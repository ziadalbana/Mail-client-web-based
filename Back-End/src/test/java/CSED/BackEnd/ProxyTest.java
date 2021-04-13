package CSED.BackEnd;

import CSED.BackEnd.Proxy.IProxy;
import CSED.BackEnd.Proxy.Proxy;
import CSED.BackEnd.Proxy.User;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
public class ProxyTest {

    @Test
    public void signUpTest(){

        IProxy p = new Proxy();
        User u = new User();
        u.setUserName("ziad.elabd91@gmail.com");
        u.setPassword("123");
        u.setAge("21");
        u.setGender("male");
        u.setPhone("01115476262");
        boolean b = p.signUp(u);
        assertTrue(b);
    }

    @Test
    public void signInTest(){
        IProxy p = new Proxy();
        User u = new User();
        boolean b = p.signIn("ziad.elabd91@gmail.com","123");
        System.out.println(b);
        assertTrue(b);
    }
}
