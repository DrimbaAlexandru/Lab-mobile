package com.awesomeproject.Repository.Rest;

import com.awesomeproject.Domain.ClassSchedule;

/**
 * Created by Alex on 28.12.2017.
 */

public class ClassScheduleDTO extends ClassSchedule
{
    private int AvailableCapacity = 0;

    public int getAvailableCapacity()
    {
        return AvailableCapacity;
    }

    public void setAvailableCapacity( int availableCapacity )
    {
        AvailableCapacity = availableCapacity;
    }

    public static ClassScheduleDTO fromClassSchedule( ClassSchedule cs )
    {
        ClassScheduleDTO dto = new ClassScheduleDTO();
        dto.setDate( cs.getDate() );
        dto.setId( cs.getId() );
        dto.setDifficulty( cs.getDifficulty() );
        dto.setTrainerId( cs.getTrainerId() );
        dto.setClassId( cs.getClassId() );
        dto.setCapacity( cs.getCapacity() );
        dto.setAvailableCapacity( cs.getCapacity() );
        dto.setRoom( cs.getRoom() );
        return dto;
    }

}
