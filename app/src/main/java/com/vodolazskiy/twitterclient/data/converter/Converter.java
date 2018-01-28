package com.vodolazskiy.twitterclient.data.converter;

import com.vodolazskiy.twitterclient.core.converter.ConvertersContextImpl;
import com.vodolazskiy.twitterclient.core.converter.IConvertersContext;


public final class Converter {
    private final IConvertersContext convertersContext;

    private Converter() {
        convertersContext = new ConvertersContextImpl();
        initContext();
    }

    public static IConvertersContext get() {
        return InstanceHolder.INSTANCE.convertersContext;
    }

    @SuppressWarnings("unchecked")
    private void initContext() {
        convertersContext.registerConverter(new UserFeedConverter());
    }

    private static final class InstanceHolder {
        private static final Converter INSTANCE = new Converter();

        private InstanceHolder() {
            //no instance
        }
    }
}
