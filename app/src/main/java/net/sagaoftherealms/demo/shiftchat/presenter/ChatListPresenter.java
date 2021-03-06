package net.sagaoftherealms.demo.shiftchat.presenter;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import net.sagaoftherealms.demo.shiftchat.ShiftChatApplication;
import net.sagaoftherealms.demo.shiftchat.model.Chat;
import net.sagaoftherealms.demo.shiftchat.model.ChatDatabase;
import net.sagaoftherealms.demo.shiftchat.model.util.DBBootstrap;
import net.sagaoftherealms.demo.shiftchat.view.ChatListActivity;

import java.util.List;

public class ChatListPresenter {

    private ChatListActivity activity;
    private ChatDatabase chatDb;

    public void attachChatActivity(ChatListActivity activity) {
        this.activity = activity;
        beginLoadChats();
    }

    private void beginLoadChats() {

        if (activity != null) {

            chatDb= ((ShiftChatApplication)activity.getApplication()).getDB();

            List<Chat> chats = chatDb.chatDao().findMostRecentChats();
            for (Chat chat : chats) {
                chat.members.addAll(chatDb.chatDao().findMembersInChat(chat.id));
                chat.messages.addAll(chatDb.chatDao().findMessagesInChat(chat.id));
            }
            activity.displayChats(chats);
        }
    }

    public void detachChatActivity() {
        this.activity = null;
    }

}
