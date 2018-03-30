package com.manager.store.service;

import java.util.List;

public interface MailService {

    void sendMessage(String messageContent, String from, List<String> to, List<String> cc);

    void sendMessage(String messageContent, String from, String[] to, String[] cc);

    void sendMessage(String messageContent, Long from, Long[] to, Long[] cc);
}
