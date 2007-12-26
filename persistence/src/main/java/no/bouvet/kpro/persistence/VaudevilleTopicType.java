package no.bouvet.kpro.persistence;

import no.bouvet.topicmap.dao.TopicType;

public enum VaudevilleTopicType implements TopicType {
	MEDIA {
		public String tologBinding() {
			return "vdvil:media";
		}

		public String psi() {
			return "https://wiki.bouvet.no/display/KPRO2007/MEDIA";
		}
	},
	EVENT {
		public String tologBinding() {
			return "vdvil:event";
		}

		public String psi() {
			return "https://wiki.bouvet.no/display/KPRO2007/EVENT";
		}
	},
    PART {
		public String tologBinding() {
			return "vdvil:part";
		}

		public String psi() {
			return "https://wiki.bouvet.no/part";
		}
	},WHOLE {
		public String tologBinding() {
			return "vdvil:whole";
		}

		public String psi() {
			return "https://wiki.bouvet.no/whole";
		}
	},
	/**
	 * In case no topicmap was found
	 */
	UNDEFINED {
		public String tologBinding() {
			return null;
		}

		public String psi() {
			return null;
		}

	};
}
