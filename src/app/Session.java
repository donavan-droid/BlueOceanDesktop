package app;

import model.Utilisateur;

public class Session {
    private static Utilisateur utilisateur;

    public static void setUtilisateur(Utilisateur u) { utilisateur = u; }
    public static Utilisateur getUtilisateur() { return utilisateur; }
    public static void clear() { utilisateur = null; }

    public static boolean estAdmin() {
        return utilisateur != null && "admin".equals(utilisateur.getRole());
    }

    public static int getIdUser() {
        return utilisateur != null ? utilisateur.getIdUser() : -1;
    }
}