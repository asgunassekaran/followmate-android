package com.example.chandramohanr.followmate.app.controller;

import com.example.chandramohanr.followmate.app.helpers.JsonParserHelper;
import com.example.chandramohanr.followmate.app.helpers.SharedPreferenceHelper;
import com.example.chandramohanr.followmate.app.models.events.response.JoinRoomResponse;
import com.example.chandramohanr.followmate.app.models.events.response.NewUserJoinedEvent;
import com.example.chandramohanr.followmate.app.models.events.response.ReconnectToPreviousLostSession;
import com.example.chandramohanr.followmate.app.models.events.response.SessionStartedEvent;
import com.github.nkzawa.emitter.Emitter;

import de.greenrobot.event.EventBus;

public class SessionEvents {

    final EventBus eventBus = EventBus.getDefault();

    public Emitter.Listener onSessionStarted = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            SessionStartedEvent sessionStartedEvent = JsonParserHelper.getSessionStartedEvent(args[0]);
            if (sessionStartedEvent.is_session_created) {
                SharedPreferenceHelper.set(SharedPreferenceHelper.KEY_IS_SESSION_ACTIVE, true);
                SharedPreferenceHelper.set(SharedPreferenceHelper.KEY_ACTIVE_SESSION_ID, sessionStartedEvent.session_id);
            }
            eventBus.post(sessionStartedEvent);
        }
    };

    public Emitter.Listener onJoinedSession = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JoinRoomResponse joinRoomResponse = JsonParserHelper.getJoinSessionResponseEvent(args[0]);
            eventBus.post(joinRoomResponse);
        }
    };

    public Emitter.Listener onNewUserJoined = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            NewUserJoinedEvent newUserJoinedEvent = JsonParserHelper.getNewUserJoinedSessionResponseEvent(args[0]);
            eventBus.post(newUserJoinedEvent);
        }
    };

    public Emitter.Listener onRejoined = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            NewUserJoinedEvent newUserJoinedEvent = JsonParserHelper.getNewUserJoinedSessionResponseEvent(args[0]);
            eventBus.post(newUserJoinedEvent);
        }
    };

    public void rejoinToPreviousActiveSession() {
        ReconnectToPreviousLostSession reconnectToPreviousLostSession = new ReconnectToPreviousLostSession();
        reconnectToPreviousLostSession.sessionId = SharedPreferenceHelper.getString(SharedPreferenceHelper.KEY_ACTIVE_SESSION_ID);
        eventBus.post(reconnectToPreviousLostSession);
    }
}
