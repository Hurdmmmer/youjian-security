package com.youjian.security.core.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *  短信验证码校验支持
 * @author shen youjian
 * @date 12/15/2018 10:07 PM
 */
@Data
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;


    /**
     *  校验用户是否正确
     * @param authentication ProviderManager 类传入 {@link SmsAuthenticationToken}
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;

        UserDetails userDetails = userDetailsService.loadUserByUsername((String) smsAuthenticationToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("Unable to get user information");
        }
        // 认证成功重新创建认证信息对象, 使用2个参数的构造函数, 表示当前用户以认证
        SmsAuthenticationToken smsAuthenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());

        smsAuthenticationResult.setDetails(smsAuthenticationToken.getDetails());

        return smsAuthenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断传入的类型是否是 SmsAuthenticationToken 类型, 如果是就可以支持
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
