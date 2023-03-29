package com.example.xgbuddy.ui

import androidx.fragment.app.Fragment

/**
 * I'm thinking it may be possible to have QS300 and XG side by side in this fragment. I'm thinking
 * about how the Parts fragment will be laid out.
 *
 * 1. Parts
 *
 *      I think the fragment could be split into two parts. On the left, is a list of midi channels 1-16
 *      and on the right will be the controls for the selected channel. The channels could contain
 *      either an XG voice or a QS300 voice.
 *
 *      The controls panel on the right will contain all the controls related to an XG channel since
 *      these can be applied whether the channel is dedicated to a QS300 voice or a regular XG voice.
 *
 *      When a channel is selected, if the channel contains a QS300 voice, a new tab will be added to the
 *      tab group - this will be for the VoiceEditFragment.
 *
 *      For QS300 voices, I'm not quite sure how to handle the stored user voices and navigating between
 *      them in the app. I think I'll save that for later.
 *
 * 2. Effects
 *
 *      Another tab will be the variation tab for all the effects controls. Will need to do some
 *      more research on how the data is organized for these to figure out the best way to organize
 *      the controls.
 *
 *      Chorus and reverb controls may be located here as well.
 *
 * 3. Midi Settings
 *
 *      I think here there will be a list of things where you can enable/disable things like
 *      pitch wheel, mod wheel, etc. Refer to the Mountain Utilities software for all the different
 *      options
 *
 * 4. Input/Output meters?
 *
 *      This is not crucial but may be a nice addition at some point.
 *
 * 5. Keyboard
 *
 *      Have a virtual keyboard for easy testing of sounds?
 *
 * That's probably all I need for tabs.
 *
 * In addition to the tab layout, this fragment should perhaps display the name of the setup in the
 * title bar, along with save/open/home icons. There should also be additional option menu items to
 * open connection status fragment (to enable/disable inputs/outputs), and settings (tbd), and all
 * note off.
 *
 * Going forward:
 *
 *      I can probably get the main layout in place and stubs for the fragments that will make up
 *      the session. I think to really begin, I will need to have the remaining data structures in
 *      place. QS300 stuff is fully defined i think, so I should try to define XG stuff next, then
 *      try to fit those into a General Midi model. The data structures will probably inform how the
 *      views are implemented so I think that will be necessary before doing any actual work on
 *      this.
 *
 *      To start, I'll add the basic layouts.
 */

class SessionFragment : Fragment() {

}