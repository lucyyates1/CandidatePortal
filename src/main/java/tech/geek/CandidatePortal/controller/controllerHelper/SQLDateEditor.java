package tech.geek.CandidatePortal.controller.controllerHelper;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

public class SQLDateEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String value) throws IllegalArgumentException{
        setValue(Date.valueOf(value));
    }
}
