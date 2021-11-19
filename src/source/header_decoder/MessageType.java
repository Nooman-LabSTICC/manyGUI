package source.header_decoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ruaro
 */
public class MessageType {
    
    private String message_name;
    private int value;
    private String description;
    
    public MessageType(String name, int value, String description){
        this.message_name = name;
        this.value = value;
        this.description = description;
    }
    
    
    public String getMessageName(){
        return this.message_name;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public String getDescription(){
        return this.description;
    }
    
}
