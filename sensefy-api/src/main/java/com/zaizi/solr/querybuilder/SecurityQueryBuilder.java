package com.zaizi.solr.querybuilder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.zaizi.dto.SensefyUser;

/**
 * This class add the security modeling in the Solr Query building this
 * accordingly to the security details
 * 
 * @Author: Alessandro Benedetti Date: 14/05/2014
 */
public class SecurityQueryBuilder {
	private static final String ALLOW_TOKEN = "allow_token_document:";

	private static final String DENY_TOKEN = "deny_token_document:";

	private static final String NOSECURITY = "__nosecurity__";

	// TODO : Temproryly removed sensefy token since it need to have a different
	// way of getting ACLS **public static String getSecurityFilterQuery(
	// SensefyToken sensefyToken )
	public static String getSecurityFilterQuery(SensefyUser user) {
		return buildUserAclsFilterQuery(user.getUserAcl(), user.getDomainAcl(), user.getUsername());
	}

	/**
	 * Build User ACLS filter query using all the Acl and the username
	 *
	 * @param userAcls
	 * @return
	 */
	private static String buildUserAclsFilterQuery(List<String> userAcls, List<String> domainUserAcls,
			String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("(+" + ALLOW_TOKEN + "\"" + NOSECURITY + "\") ");
		populateAclBooleanClause(userAcls, sb);
		populateAclBooleanClause(domainUserAcls, sb);
		if (username != null) {
			sb.append(ALLOW_TOKEN + username);
		}
		return sb.toString();
	}

	/**
	 * Builds a Solr Boolean Query with the ACl in input
	 * 
	 * @param userAcls
	 * @param sb
	 */
	private static void populateAclBooleanClause(List<String> userAcls, StringBuilder sb) {
		if (userAcls != null && !userAcls.isEmpty()) {
			sb.append("(");
			for (String acl : userAcls) {
				String parsedACL = escapeAclString(acl);
				sb.append(ALLOW_TOKEN + "\"" + parsedACL + "\" -" + DENY_TOKEN + "\"" + parsedACL + "\" ");
			}
			sb.append(")");
		}
	}

	/**
	 * Escape the Acl string in a proper way to be included in a Solr Query
	 *
	 * @param s
	 * @return
	 */
	private static String escapeAclString(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// These characters are part of the query syntax and must be escaped
			if (c == ':') {
				sb.append('\\');
				sb.append(c);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
