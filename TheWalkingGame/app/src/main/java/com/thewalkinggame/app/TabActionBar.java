package com.thewalkinggame.app;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.thewalkinggame.app.utilitaire.MyAlertDialog;


public class TabActionBar extends FragmentActivity {
    /**
     * Listener correspondant au tab qui contiendra la map
     */
    TabListener<MapFragment> tl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_action_bar);

        ActionBar actionBar = getActionBar();
        /**
         * Choix de l'action bar comme etant constitué de plusieurs tab.
         */

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        /**
         * Creation des differents tab que l'on affichera.
         */

        String label1 = getResources().getString(R.string.label1);
        ActionBar.Tab tab = actionBar.newTab();
        tab.setText(label1);
        TabListener<ScoreFragment> tl = new TabListener<ScoreFragment>(this,
                label1, ScoreFragment.class);
        tab.setTabListener(tl);
        actionBar.addTab(tab);

        String label2 = getResources().getString(R.string.label2);
        tab = actionBar.newTab();
        tab.setText(label2);
        TabListener<InfoFragment> tl2 = new TabListener<InfoFragment>(this,
                label2, InfoFragment.class);
        tab.setTabListener(tl2);
        actionBar.addTab(tab);

        String label3 = getResources().getString(R.string.label3);
        tab = actionBar.newTab();
        tab.setText(label3);
        tl3 = new TabListener<MapFragment>(this,
                label3, MapFragment.class);
        tab.setTabListener(tl3);
        actionBar.addTab(tab);
    }

    /**
     * Lorsque le joueur click sur le bouton BACK de sa tablette
     * s'il est dans une des sections de la tabActionBar, alors
     * il peut quitter la partie en cours, et donc revenir au menu.
     */
    public void onBackPressed(){
        new MyAlertDialog(
                "Info",
                "Voulez-vous vraiment quitter la partie en cours ?",
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
                        // Emmene le joueur au menu
                        tl3.removeFragment();
                        TabActionBar.this.finish();
                    }
                },
                TabActionBar.this).show();
    }


    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        /**
         * Definition d'un fragment
         */
        private android.support.v4.app.Fragment mFragment;
        /**
         * Definition d'un fragment activity
         */
        private final FragmentActivity mActivity;
        /**
         * Identifiant du fragment
         */
        private final String mTag;
        /**
         * classe du fragment
         */
        private final Class<T> mClass;
        private final Bundle mArgs;
        /**
         * Mise en place d'un fragment transaction pour gerer les fragments
         */
        private FragmentTransaction fft;

        public static final String TAG = TabListener.class.getSimpleName();

        /**
         * Le constructeur sera utilise a chaque fois qu'un nouveau tab sera cree
         *
         * @param activity
         *            L'activite actuelle est utilisee pour instancier le fragment
         * @param tag
         *            Le tag identifiant du fragment
         * @param clz
         *            La classe du fragment, utilisee pour instancier le fragment
         */

        public TabListener(FragmentActivity activity, String tag, Class<T> clz) {

            this(activity, tag, clz, null);
        }
        public TabListener(FragmentActivity activity, String tag, Class<T> clz, Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;

            mFragment = mActivity.getSupportFragmentManager()
                    .findFragmentByTag(mTag);
            if (mFragment != null && !mFragment.isDetached()) {

                /**
                 * Detache le fragment fft puis applique ce detachement
                 */
                fft = mActivity.getSupportFragmentManager().beginTransaction();
                fft.detach(mFragment);
                fft.commit();
                mActivity.getSupportFragmentManager()
                        .executePendingTransactions();
            }
        }

        /**
         *          Lorsque que l'on va selectionner un tab cette fonction va definir ce qu'il se passe
         * @param tab
         *              defini le tab vise
         * @param ft
         *              appel FragmentTransaction
         */
        @Override
        public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            Fragment tmp = mActivity.getSupportFragmentManager()
                    .findFragmentByTag(mTag);
            fft = mActivity.getSupportFragmentManager().beginTransaction();
            /**
             * Ajoute un fragment puis applique cet ajout si le fragment n'existe pas
             */
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                fft.add(android.R.id.content, mFragment, mTag);
                fft.commit();
                mActivity.getSupportFragmentManager()
                        .executePendingTransactions();
            } else {
                /**
                 * Si le fragment existe, on le montre seulement
                 */
                fft.show(mFragment);
                fft.commit();
                mActivity.getSupportFragmentManager()
                        .executePendingTransactions();
            }
        }

        /**
         *          Lorsqu'un tab est deselectionne cette fonction va definir ce qu'il se passe
         * @param tab
         *              defini le tab vise
         * @param ft
         *              appel FragmentTransaction
         */
        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            mFragment = mActivity.getSupportFragmentManager()
                    .findFragmentByTag(mTag);
            /**
             * lorsqu'on change de tab, donc que l'actuel tab est deselectionne on le cache seulement pour garder
             * le reste des fonctions actives
             */
            if (mFragment != null && !mFragment.isDetached()) {
                fft = mActivity.getSupportFragmentManager().beginTransaction();
                fft.hide(mFragment);
                fft.commit();
                mActivity.getSupportFragmentManager()
                        .executePendingTransactions();
            }
        }

        /**
         *
         * @param tab le tab qui est reselectionne
         * @param ft
         *
         * Rien de special ne se passe lorsqu'on retourne sur un tab.
         */
        @Override
        public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

        }

        /**
         * Fonction qui va supprimer le fragment
         */
        public void removeFragment(){
            mFragment = mActivity.getSupportFragmentManager()
                    .findFragmentByTag(mTag);
            if (mFragment != null ) {

                fft = mActivity.getSupportFragmentManager().beginTransaction();
                fft.remove(mFragment);
                fft.commit();
            }
        }

    }

}
