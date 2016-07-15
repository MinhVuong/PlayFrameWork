package business;

import entities.TokenEntity;
import java.util.UUID;

public class TokenHelper {

	// generate tokenEntity from Customer ID
	public TokenEntity CreateTokenForCustomerID_EMAIL(int id) {
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setCustomer_id(id);
		tokenEntity.setType(1);
		tokenEntity.setToken(UUID.randomUUID().toString());

		DateHelper dateHelper = new DateHelper();

		String time = dateHelper.getDateTimeCurrent();
		tokenEntity.setCreateAt(time);
		tokenEntity.setExpiryDate(dateHelper.GetDateTimeExpiryEmail(time));

		return tokenEntity;
	}

	// generate tokenEntity from Customer ID
	public TokenEntity CreateTokenForAdmin(int id) {
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setCustomer_id(id);
		tokenEntity.setType(2);
		tokenEntity.setToken(UUID.randomUUID().toString());

		DateHelper dateHelper = new DateHelper();

		String time = dateHelper.getDateTimeCurrent();
		tokenEntity.setCreateAt(time);
		tokenEntity.setExpiryDate(dateHelper.GetDateTimeExpiryEmail(time));

		return tokenEntity;
	}
}
