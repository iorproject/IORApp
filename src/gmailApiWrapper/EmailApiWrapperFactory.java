package gmailApiWrapper;

import com.sun.istack.Nullable;

public class EmailApiWrapperFactory {

    public static IEmailApiWrapper createEmailApiWrapper
            (eEmailApi api, String userId, @Nullable String accessToken, @Nullable String refreshToken) throws Exception {


        IEmailApiWrapper wrapper = null;

        switch (api) {

            case GMAIL:
                wrapper = new GmailApiWrapper(userId, accessToken, refreshToken);
                break;

            case YAHOO:
                break;

        }

        return wrapper;
    }


}
