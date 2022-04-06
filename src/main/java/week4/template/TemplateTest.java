package week4.template;

import org.junit.Assert;
import org.junit.Test;

public class TemplateTest {
    @Test
    public void test0(){
        Assert.assertEquals("1a2a3", new Template("1?2?3", 1).solve());
    }

    @Test
    public void test1(){
        Assert.assertEquals("abac", new Template("ab?c", 1).solve());
        Assert.assertEquals("abdc", new Template("ab?c", 4).solve());
    }

    @Test
    public void test2(){
        Assert.assertEquals("adecabedeba", new Template("a??cab?d?ba", 500).solve());
    }

    @Test
    public void test3(){
        Assert.assertEquals("abebbdcbddcbbdebcdbbdadaeea", new Template("?be?bdcb?dcb?debcd?bdad?ee?", 5151).solve());
    }

}
