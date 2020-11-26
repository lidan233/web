package com.lidan.job;
import com.lidan.Model.User;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

public class ObjecttoString {

    public static GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
            new GenericJackson2JsonRedisSerializer() ;
    public static String ObjecttoString(String i )
    {
        byte[] bytes = i.getBytes();

        i  = (String)genericJackson2JsonRedisSerializer.deserialize(bytes) ;
        return i ;
    }

    public static User ObjecttoUser(String i)
    {
        byte[] bytes = i.getBytes();

        return (User)genericJackson2JsonRedisSerializer.deserialize(bytes) ;

    }
}
