package com.mradkingshop.vsssn.admin.modal;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

/**
 * Created by Dell on 18-Mar-18.
 */

public class DocId {

    @Exclude
    public String id;

    public <T extends DocId> T withId(@NonNull final String id) {
        this.id = id;
        return (T) this;
    }


}
