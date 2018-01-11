package nyc.c4q.androidtest_unit4final;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {

  @GET("/operable/cog/master/priv/css-color-names.json")
  Call<HashMap<String, String>> fetchColors();
}
