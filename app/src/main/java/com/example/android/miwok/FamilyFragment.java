package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FamilyFragment extends Fragment {


    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;


    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMediaPlayer.start();
                    }
                }
            };


    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public FamilyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> family = new ArrayList<Word>();

        family.add(new Word("father", getString(R.string.miwokFather), R.drawable.family_father, R.raw.family_father));
        family.add(new Word("mother", getString(R.string.miwokMother), R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("son", getString(R.string.miwokSon), R.drawable.family_son, R.raw.family_son));
        family.add(new Word("daughter", getString(R.string.miwokDaughter), R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("older brother", getString(R.string.miwokOlderBrother), R.drawable.family_older_brother, R.raw.family_older_brother));
        family.add(new Word("younger brother", getString(R.string.miwokYoungerBrother), R.drawable.family_younger_brother, R.raw.family_younger_brother));
        family.add(new Word("older sister", getString(R.string.miwokOlderSister), R.drawable.family_older_sister, R.raw.family_older_sister));
        family.add(new Word("younger sister", getString(R.string.miwokYoungerSister), R.drawable.family_younger_sister, R.raw.family_younger_sister));
        family.add(new Word("grandmother", getString(R.string.miwokGrandMother), R.drawable.family_grandmother, R.raw.family_grandmother));
        family.add(new Word("grandfather", getString(R.string.miwokGrandfather), R.drawable.family_grandfather, R.raw.family_grandfather));





        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);

//        ArrayAdapter<Word> itemsAdapter = new ArrayAdapter<Word>(this, R.layout.list_item, words);

        WordAdapter adapter = new WordAdapter(getActivity(), family, R.color.category_family);


        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

//        GridView gridView = (GridView) findViewById(R.id.grid);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
//        listView.setAdapter(itemsAdapter);

        listView.setAdapter(adapter);
//        gridView.setAdapter(itemsAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_one);
//                mMediaPlayer.start();

                // Release the media player if it currently exists because we are about to play a
                // a different sound file.
                releaseMediaPlayer();

                //  Create and setup the {@link MediaPlayer} for the audio resource associated with
                //  the current clicked word.
                Word clickedWord = family.get(position);



                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now


                    mMediaPlayer = MediaPlayer.create(getActivity(), clickedWord.getSoundResourceId());
                    mMediaPlayer.start();

                    // Setup a listener a listener on the media player, so that we can stop and release
                    // the media player once sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                    //              // Instead of implementing it in this way, we have created this as a global function
                    //              // so that we don't have to create a new instance of MediaPlayer.OncompletionListener every time

                    //                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    //                    @Override
                    //                    public void onCompletion(MediaPlayer mp) {
                    //                        Toast.makeText(FamilyActivity.this, "I'm done!", Toast.LENGTH_SHORT).show();
                    //                        releaseMediaPlayer();
                    //                    }
                    //                });
                }
            }




        });

        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}