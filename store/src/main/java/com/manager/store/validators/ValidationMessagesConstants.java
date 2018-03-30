package com.manager.store.validators;


/**
 * A class containing all constansts with validation messages.
 *
 * @author Piotr
 */
@SuppressWarnings("unused")
public class ValidationMessagesConstants {

	public static final String MESSAGE_INVALID_PASSWORD =
			"Password must contains one digit and lowercase char. and upperrcase char. and special symbols."
			+ " Length 6-10 characters.";

	public static final String MESSAGE_INVALID_DATE = "Wrong format date ! eg. 2016-01-01";

	public static final String MESSAGE_INVALID_TELEPHONE = "Wrong format telephone number ! eg. +48 123456789";

	public static final String MESSAGE_INVALID_STORE_NAME = "Wrong format store name ! Min. 4 letters";

	public static final String MESSAGE_INVALID_NAME_4 = "Wrong format name ! Min. 4 letters";

	public static final String MESSAGE_INVALID_NAME_2 = "Wrong format name ! Min. 2 letters";

	public static final String MESSAGE_INVALID_PRICE = "Wrong format price ! eg. 120.20";

	public static final String MESSAGE_INVALID_FIRSTNAME = "Wrong format firstname ! Min. 3 letters";

	public static final String MESSAGE_INVALID_LASTNAME = "Wrong format lastname ! Min. 3 letters";

	public static final String ALPHANUMERIC_MIN_3_SIGN_REGEX = "[A-Z][a-zA-Z0-9]{2}.*";

	public static final String ALPHANUMERIC_MIN_4_SIGN_REGEX = "[A-Z][a-zA-Z0-9]{3}.*";

	public static final String ALPHANUMERIC_SPACED_MIN_4_SIGN_REGEX = "[A-Z][a-zA-Z0-9 ]{3}.*";

	public static final String ALPHANUMERIC_MIN_2_SIGN_REGEX = "[A-Z][a-zA-Z0-9].*";

	public static final String TELEPHONE_REGEX = "\\+?\\d{5,}";

	public static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,10})";

	public static final String DATE_REGEX ="(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])";

    /** Uppercase regex. */
    public static final String UPPERCASE_ALPHA_REGEX = "^[A-Z0-9]*$";

    /** The Constant NUMERIC_REGEX. */
    public static final String NUMERIC_REGEX = "^[0-9]*$";

    /** Alphanumeric regex. */
    public static final String ALPHANUMERIC_REGEX = "^[A-Za-z0-9 ]*$";

    /** Price regex. */
    public static final String PRICE_REGEX = "^-?[0-9]{1,6}(\\.[0-9]{1,2})?$";

    /** The Constant PRICE_REGEX_OR_EMPTY. */
    public static final String PRICE_REGEX_OR_EMPTY = "(" + PRICE_REGEX + ")?";

    /** Positive Price regex. */
    public static final String POSITIVE_PRICE_REGEX = "^[0-9]{1,6}(\\.[0-9]{1,2})?$";

    /** The Constant POSITIVE_PRICE_REGEX_OR_EMPTY. */
    public static final String POSITIVE_PRICE_REGEX_OR_EMPTY = "(" + POSITIVE_PRICE_REGEX + ")?";

    /**
     * Used typically for a percentage, e.g.: 1234.56
     */
    public static final String DECIMAL_SIX_TWO_REGEX = "^-?(0|[1-9]([0-9]{0,3}))(\\.[0-9]{1,2})?$";

    /** The Constant DECIMAL_SIX_TWO_REGEX_OR_EMPTY. */
    public static final String DECIMAL_SIX_TWO_REGEX_OR_EMPTY = "(" + DECIMAL_SIX_TWO_REGEX + ")?";

    public static final String DATE_ONLY_DATE_PATTERN = "yyyy-MM-dd";

    public static final String DATE_ONLY_DATE_PATTERN2 = "dd.MM.yyyy";

    public static final String MESSAGE_E_MAIL_IN_USE = "This e-mail is already in use by an active user.";

    public static final String E_MAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public static final String MESSAGE_E_MAIL_FORMAT = "E-mail format is incorrect.";

    public static final String MESSAGE_UNIQUE_STORE_NAME = "A store with provided name already exists.";
}
