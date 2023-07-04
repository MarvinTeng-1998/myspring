package com.marvin.event;

import com.marvin.springframework.context.event.ApplicationContextEvent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 15:30
 **/
@Data
@Setter
@Getter
public class CustomEvent extends ApplicationContextEvent {
    private Long id;
    private String message;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public CustomEvent(Object source,Long id,String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

}
