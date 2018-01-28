package com.vodolazskiy.twitterclient.core.converter;

import android.support.annotation.NonNull;

public interface IConvertersContextVisitor {
    void visit(@NonNull IConvertersContext convertersContext);
}
