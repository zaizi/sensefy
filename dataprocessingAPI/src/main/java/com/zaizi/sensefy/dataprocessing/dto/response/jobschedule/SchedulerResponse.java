package com.zaizi.sensefy.dataprocessing.dto.response.jobschedule;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zaizi.sensefy.dataprocessing.dto.response.AbstractDataprocessingResponse;

@XmlRootElement
@XmlType( propOrder = { "header", "error" } )
public class SchedulerResponse extends AbstractDataprocessingResponse{

}
