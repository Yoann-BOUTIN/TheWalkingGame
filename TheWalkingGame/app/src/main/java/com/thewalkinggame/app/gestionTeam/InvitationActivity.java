package com.thewalkinggame.app.gestionTeam;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.Team;
import com.thewalkinggame.app.utilitaire.MyAlertDialog;

import java.util.HashMap;
import java.util.LinkedList;


public class InvitationActivity extends Activity {

    private LinkedList<HashMap<String, String>> linkedList;
    private ListView invitationList;
    private SimpleAdapter mSchedule;
    private Button refuseAllInvitations;

    String memoirePseudo;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        // On va chercher en mémoire le pseudo du joueur qui lance l'activité.
        settings = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editor = settings.edit();
        memoirePseudo = settings.getString("pseudo", "");

        linkedList = new LinkedList<HashMap<String, String>>();

        invitationList = (ListView) findViewById(android.R.id.list);

        new RecupInvitations(InvitationActivity.this).execute();

        // Bouton qui permet de refuser toutes les invitations
        refuseAllInvitations = (Button) findViewById(R.id.refuser_toutes_les_invitations);
        refuseAllInvitations.setOnClickListener(listennerRefuseAllInvitations);
    }

    /**
     * Permet de mettre à jour le journal des invitations.
     * Permet d'accepter ou de refuser une invitation.
     *
     * @param linkedList La liste qui contient les invitations (le couple :
     *                   date et inviteur).
     */
    public void updateJournal(final LinkedList<HashMap<String, String>> linkedList){

        // Je crée un objet simpleAdapter avec le fichier xml des item affiche_invitation.xml
        mSchedule = new SimpleAdapter(this, linkedList, R.layout.affiche_invitation,
                new String[] {"id","date", "titre", "accepter", "refuser"}, new int[]
                {R.id.idInvit, R.id.date, R.id.pseudo_inviteur, R.id.accepter_invitation, R.id.refuser_invitation});

        // setListAdapter(new SpeechListAdapter(this));
        invitationList.setAdapter(mSchedule);

        mSchedule.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, final String textRepresentation) {

                if (view.getId()== R.id.accepter_invitation){
                    // Le joueur accepte l'invitation
                    Button b = (Button) view;
                    final RelativeLayout mRelativeLayout = (RelativeLayout) b.getParent();
                    final String idInvit = (String)((TextView) mRelativeLayout.findViewById(R.id.idInvit)).getText();
                    final String date = (String) ((TextView) mRelativeLayout.findViewById(R.id.date)).getText();
                    final String phraseInviteur = (String) ((TextView) mRelativeLayout.findViewById(R.id.pseudo_inviteur)).getText();
                    // On recupere le pseudo de l'inviteur qui est le premier "mot" de la phrase d'invitation
                    final String pseudoInviteur = phraseInviteur.split(" ")[0];
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AccepteInvitation(InvitationActivity.this, pseudoInviteur, idInvit).execute();
                        }
                    });
                    return true;

                } else if (view.getId()== R.id.refuser_invitation){
                    // Le joueur refuse l'invitation
                    Button b = (Button) view;
                    final RelativeLayout mRelativeLayout = (RelativeLayout) b.getParent();
                    final String idInvit = (String)((TextView) mRelativeLayout.findViewById(R.id.idInvit)).getText();
                    final String date = (String) ((TextView) mRelativeLayout.findViewById(R.id.date)).getText();
                    final String phraseInviteur = (String) ((TextView) mRelativeLayout.findViewById(R.id.pseudo_inviteur)).getText();
                    // On recupere le pseudo de l'inviteur qui est le premier "mot" de la phrase d'invitation
                    final String pseudoInviteur = phraseInviteur.split(" ")[0];
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new RefuseInvitation(InvitationActivity.this, idInvit).execute();
                        }
                    });
                    return true;
                }

                return false;
            }
        });

    }

    /**
     * Permet de refuser toutes les invitations reçues si le joueur clique
     * sur le bouton "Oui".
     * La page sera actualisée, et elle sera alors vide.
     */
    View.OnClickListener listennerRefuseAllInvitations = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new MyAlertDialog(
                    "Invitations",
                    "Refuser toutes les invitations ?",
                    R.drawable.icon_j1,
                    "Non",
                    "Oui",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // nothing
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new RefuseAllInvitations(InvitationActivity.this).execute();
                        }
                    },
                    InvitationActivity.this
            ).show();
        }
    };

}
