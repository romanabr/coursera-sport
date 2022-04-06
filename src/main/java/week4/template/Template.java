package week4.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Template {
    private static final Logger logger = LoggerFactory.getLogger(Template.class);
    private final String template;
    private final int number;
    private int counter;
    private String result = "";

    public Template(String template, int number) {
        this.template = template;
        this.number = number;
    }

    public String solve() {
        int count = 0;
        for (int i = 0; i < template.length(); i++) {
            if(template.charAt(i) == '?'){
                count ++;
            }
        }
        logger.info("? counts: {}" , count);
        char[] chars = new char[count];
        rec(chars, 0);

        return result;
    }

    private void rec(char[] chars, int index) {
        char[] src = {'a', 'b', 'c', 'd', 'e'};

        for (int i = 0; i < src.length; i++) {
            chars[index] = src[i];
            if (index + 1 == chars.length) {
                print(chars);
                logger.debug("{}", new String(chars));
            } else {
                rec(chars, index + 1);
            }
        }
    }

    private void print(char[] chars){
        StringBuilder output = new StringBuilder();
        int count = 0;
        for (int i = 0; i < template.length(); i++) {
            if(template.charAt(i) == '?'){
                output.append(chars[count++]);
            }else{
                output.append(template.charAt(i));

            }
        }

        counter++;
        if(counter==number){
            result = output.toString();
        }

        logger.debug("result: {}", output);

    }
}
