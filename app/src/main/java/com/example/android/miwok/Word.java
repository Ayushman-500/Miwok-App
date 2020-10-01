package com.example.android.miwok;

public class Word {

    /** Default Translation for the word*/
    private String mDefaultTranslation;

    /** Miwok Trnaslation for the word*/
    private String mMiwokTranslation;

    /** Image resource Id for the word*/
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /** Sound resource Id for the word*/
    private int mSoundResourceId;

    public Word(String DefaultTranslation, String MiwokTranslation, int SoundResourceId){
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mSoundResourceId = SoundResourceId;
    }

    public Word(String DefaultTranslation, String MiwokTranslation, int ImageResourceId, int SoundResourceId){
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mImageResourceId = ImageResourceId;
        mSoundResourceId = SoundResourceId;
    }
    /**
     * Get the default translation of the word
     */
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word
     */
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getSoundResourceId(){
        return mSoundResourceId;
    }

    /**
     * you can create this function via the shortcut (windows + fn + insert)
     * This function is useful for debugging
     * @return readable string format of the object
     */
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mSoundResourceId=" + mSoundResourceId +
                '}';
    }
}
