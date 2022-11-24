package cryodex.modules.xwing;

import java.util.ArrayList;
import java.util.List;
import cryodex.xml.XMLObject;
import cryodex.xml.XMLUtils;
import cryodex.xml.XMLUtils.Element;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;

public class XWingRound implements XMLObject {
	private List<XWingMatch> matches;
	private XWingRoundPanel panel;
	private Boolean isSingleElimination = false;

	public XWingRound(Element roundElement, XWingTournament t) {
		this.isSingleElimination = roundElement
				.getBooleanFromChild("ISSINGLEELIMINATION");

		Element matchElement = roundElement.getChild("MATCHES");

		if (matchElement != null) {
			matches = new ArrayList<>();
			for (Element e : matchElement.getChildren()) {
				matches.add(new XWingMatch(e));
			}
		}

		this.panel = new XWingRoundPanel(t, matches);
	}

	public XWingRound(List<XWingMatch> matches, XWingTournament t) {
		this.matches = matches;
		this.panel = new XWingRoundPanel(t, matches);
	}

	public List<XWingMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<XWingMatch> matches) {
		this.matches = matches;
	}

	public Node getPanel() {
		return panel.getPanel();
	}

	public void setPanel(XWingRoundPanel panel) {
		this.panel = panel;
	}

	public void setSingleElimination(boolean isSingleElimination) {
		this.isSingleElimination = isSingleElimination;
	}

	public boolean isSingleElimination() {
		return isSingleElimination != null && isSingleElimination;
	}

	@Override
	public StringBuilder appendXML(StringBuilder sb) {

		XMLUtils.appendObject(sb, "ISSINGLEELIMINATION", isSingleElimination());
		XMLUtils.appendList(sb, "MATCHES", "MATCH", getMatches());

		return sb;
	}

	public boolean isComplete() {
		for (XWingMatch m : getMatches()) {
			if (!m.isMatchComplete()) {
				return false;
			}
		}
		return true;
	}

	public boolean isValid(boolean isSingleElimination) {
		boolean result = true;
		for (XWingMatch m : getMatches()) {
			if (!m.isValidResult(isSingleElimination)) {
				result = false;
				break;
			}
		}

		panel.markInvalid(isSingleElimination);

		return result;
	}
}
