package com.amg.exchange.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.amg.exchange.validator.AlphaNumericValidator")
public class AlphaNumericValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String compValue = null;
		boolean flag = false;
		if (component != null && component instanceof HtmlInputText) {
			compValue = (String) ((HtmlInputText) component).getSubmittedValue();
		} else if (component != null && component instanceof HtmlInputTextarea) {
			compValue = (String) ((HtmlInputTextarea) component).getSubmittedValue();
		}
		if (compValue != null && !compValue.equalsIgnoreCase("")) {
			flag = compValue.matches("[a-zA-Z0-9]*");
		}
		if (!flag) {
			if (component instanceof HtmlInputText) {
				((HtmlInputText) component).setTitle("No special symbols are allowed here");
			} else if (component instanceof HtmlInputTextarea) {
				((HtmlInputTextarea) component).setTitle("No special symbols are allowed here");
			} else if (component instanceof HtmlSelectOneMenu) {
				((HtmlSelectOneMenu) component).setTitle("Page got some un-conditional Data");
			}
			throw new ValidatorException(new FacesMessage("No special symbols are allowed"));
		}

	}

}
