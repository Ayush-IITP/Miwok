package com.example.hp.miwok;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment{
    private MediaPlayer mMymedia;
    private AudioManager am;
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        mMymedia.stop();
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback
                        mMymedia.pause();
                        mMymedia.seekTo(0);
                    } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, keep playing
                        mMymedia.pause();
                        mMymedia.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMymedia.start();
                    }
                }
            };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_layout_list_view,container,false);
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words = new ArrayList<word>();
        words.add( new word("one","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new word("two","ottiko",R.drawable.number_two,R.raw.number_two));
        words.add(new word("three","tooloikosu",R.drawable.number_three,R.raw.number_three));
        words.add(new word("four","oyissa",R.drawable.number_four,R.raw.number_four));
        words.add(new word("five","masookka",R.drawable.number_five,R.raw.number_five));
        words.add(new word("six","tammoka",R.drawable.number_six,R.raw.number_six));
        words.add(new word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));
        wordadapter itemsAdapter = new wordadapter(getActivity(),words,R.color.category_numbers);
        ListView listView = (ListView)rootView.findViewById(R.id.common_list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word wordclicked = words.get(i);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMymedia = MediaPlayer.create(getActivity(), wordclicked.getMaudioid());
                    mMymedia.start();
                    mMymedia.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return  rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMymedia != null) {// Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMymedia.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment
            mMymedia = null;
            am.abandonAudioFocus(afChangeListener);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(getActivity());
                if (NavUtils.shouldUpRecreateTask(getActivity(), upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(getActivity())
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(getActivity(), upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
