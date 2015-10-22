package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.delete.DeleteAllPlayers;
import com.thewalkinggame.app.admin.delete.DeletePlayer;
import com.thewalkinggame.app.admin.get.FetchUsers;
import com.thewalkinggame.app.utilitaire.MyAlertDialog;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Marc on 30/04/2014.
 *
 * Activity dans laquelle l'admin peut modifier
 * les données des joueurs.
 */
public class ManagePlayersActivity extends Activity {
    /**
     * ListView des joueurs de TheWalkingGame
     */
    ListView listViewPlayers = null;
    /**
     * Permet de gérer la ListView
     */
    private SimpleAdapter mSchedule;
    /**
     * LinkedList qui contient tous les joueurs.
     */
    LinkedList<HashMap<String, String>> linkedList;
    /**
     * Le bouton qui permet de supprimer
     * tous les joueurs.
     */
    private Button deleteAllUsers = null;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_players);

        linkedList = new LinkedList<HashMap<String, String>>();

        listViewPlayers = (ListView) findViewById(R.id.listViewUsers);
        deleteAllUsers = (Button)findViewById(R.id.deleteAllUsers);
        deleteAllUsers.setOnClickListener(deleteAll);

        new FetchUsers(ManagePlayersActivity.this).execute();
    }

    /**
     * Affiche tous les joueurs
     *
     * @param linkedList liste de tous les joueurs
     */
    public void showPlayers(final LinkedList<HashMap<String, String>> linkedList) {

        mSchedule = new SimpleAdapter(this, linkedList, R.layout.show_players,
                new String[] {"delete","pseudo", "delete"}, new int[]
                {R.id.textview_idUser,R.id.textview_player, R.id.delete_user});

        listViewPlayers.setAdapter(mSchedule);

        mSchedule.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, final String textRepresentation) {

                if (view.getId()== R.id.delete_user){
                    Button button = (Button) view;
                    RelativeLayout relativeLayout = (RelativeLayout)button.getParent();
                    final String id = (String) ((TextView) relativeLayout.findViewById(R.id.textview_idUser)).getText();
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MyAlertDialog(
                                    "WARNING",
                                    "Voulez-vous vraiment supprimer ce joueur?",
                                    R.drawable.icon_caution,
                                    "Confirmer",
                                    "Annuler",
                                    new DialogInterface.OnClickListener() {
                                        // Lors d'un click sur le bouton "OK" de la boite de dialogue
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            new DeletePlayer(ManagePlayersActivity.this, Integer.parseInt(id)).execute();
                                        }
                                    },
                                    new DialogInterface.OnClickListener() {
                                        // Lors d'un click sur le bouton "OK" de la boite de dialogue
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    },
                                    ManagePlayersActivity.this).show();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Listener qui permet de supprimer tous les utilisateurs
     */
    View.OnClickListener deleteAll = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new MyAlertDialog(
                    "WARNING",
                    "Voulez-vous vraiment supprimer ce joueur?",
                    R.drawable.icon_caution,
                    "Confirmer",
                    "Annuler",
                    new DialogInterface.OnClickListener() {
                        // Lors d'un click sur le bouton "OK" de la boite de dialogue
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            new DeleteAllPlayers(ManagePlayersActivity.this).execute();
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        // Lors d'un click sur le bouton "OK" de la boite de dialogue
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    },
                    ManagePlayersActivity.this).show();
        }
    };
}
