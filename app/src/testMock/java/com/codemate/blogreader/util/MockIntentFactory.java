package com.codemate.blogreader.util;

import android.content.Context;
import android.content.Intent;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ironman on 05/08/16.
 */
public class MockIntentFactory {
    public static void mockFactory(Intent intent) {
        IntentFactory.instance = mockIntentFactory(intent);
    }

    public static void reset() {
        IntentFactory.instance = new IntentFactory();
    }

    private static IntentFactory mockIntentFactory(Intent intent) {
        IntentFactory factory = mock(IntentFactory.class);

        //noinspection unchecked
        when(factory.create(any(Context.class), (Class<? extends Context>) any())).thenReturn(intent);

        return factory;
    }
}
