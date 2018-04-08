/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import entity.Candidate;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author KaiCheng
 */
@FacesConverter(value = "candidateConverter", forClass = Candidate.class)
public class CandidateConverter implements Converter {

    public CandidateConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().length() == 0 || value.equals("null")) {
            return null;
        }
        try {
            List<Candidate> listCandidate = (List<Candidate>) context.getExternalContext().getSessionMap().get("listCandidate");
            for (Candidate candidate : listCandidate) {
                if (candidate.getVote() == 0 && candidate.getCandidateName().equals(value)) {
                    return candidate;
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Please select a valid value");
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof String) {
            return "";
        }
        if (value instanceof Candidate) {
            Candidate c = (Candidate) value;
            try {
                return c.getCandidateName();
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid value");
            }
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }

}
