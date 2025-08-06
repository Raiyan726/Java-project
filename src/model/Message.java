package model;

public interface Message {
    String getType();
    int calculateResponseTime(); 
    String toString();
}
