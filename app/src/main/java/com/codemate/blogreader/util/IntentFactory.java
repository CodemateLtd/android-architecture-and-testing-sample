package com.codemate.blogreader.util;

import android.content.Context;
import android.content.Intent;

/**
 * A class for creating Intents, that can be intercepted during unit testing.
 * See the testMock/util/MockIntentFactory.java and testMock/PostDetailActivityTest.java under the mock Build Variant.
 */
public class IntentFactory {
    static IntentFactory instance = new IntentFactory();

    IntentFactory() {}

    public static Intent createIntent(Context context, Class<? extends Context> clazz) {
        return IntentFactory.instance.makeIntent(context, clazz);
    }

    Intent makeIntent(Context context, Class<? extends Context> clazz) {
        return new Intent(context, clazz);
    }
}
