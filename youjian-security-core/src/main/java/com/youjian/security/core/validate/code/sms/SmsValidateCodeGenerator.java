package com.youjian.security.core.validate.code.sms;

import com.youjian.security.core.validate.code.ValidateCode;
import com.youjian.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 默认的短信验证码实现
 * @author shen youjian
 * @date 12/13/2018 8:53 PM
 */
@Component
public class SmsValidateCodeGenerator extends ValidateCodeGenerator {

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        return new ValidateCode(createRandom(securityProperties.getCode().getSms().getLength()),
                super.securityProperties.getCode().getSms().getExpiredIn());
    }

    private static String createRandom(int length){
        String retStr = "";
        String strTable = "1234567890";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

}
