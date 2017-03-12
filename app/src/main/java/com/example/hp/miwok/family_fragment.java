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
public class family_fragment extends Fragment {
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

    public family_fragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.common_layout_list_view,container,false);
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> familyArrayList = new ArrayList<word>();
        familyArrayList.add( new word("father","әpә",R.drawable.family_father,R.raw.family_father));
        familyArrayList.add( new word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        familyArrayList.add( new word("son","angsi",R.drawable.family_son,R.raw.family_son));
        familyArrayList.add( new word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        familyArrayList.add( new word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyArrayList.add( new word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyArrayList.add( new word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyArrayList.add( new word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyArrayList.add( new word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyArrayList.add( new word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        wordadapter familyadapter = new wordadapter(getActivity(),familyArrayList,R.color.category_family);
        ListView listView = (ListView)rootview.findViewById(R.id.common_list);
        listView.setAdapter(familyadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word wordclicked = familyArrayList.get(i);
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
        return rootview;
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
            // is not configured to play an audio file at the moment.
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
