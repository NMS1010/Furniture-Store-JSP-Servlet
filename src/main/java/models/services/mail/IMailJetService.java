package models.services.mail;

public interface IMailJetService {
    void sendMail(String name, String email, String content, String title);
}
