package com.kakaopay.url_shortening;

import com.kakaopay.url_shortening.util.ShorteningKeyHelper;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShorteningApplication.class)
public class UrlShorteningApplicationTests {

	@Autowired
	private ResourceLoader loader;

	private List<String> urlList;
	private List<String> mixedInvalidUrlList;

	@Before
	public void setUp() {
		urlList = new ArrayList<>();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(loader.getResource("classpath:static/urlListForTest").getInputStream()));
			urlList = bufferedReader.lines().collect(Collectors.toList());

			bufferedReader = new BufferedReader(new InputStreamReader(loader.getResource("classpath:static/mixedWrongUrlListForTest").getInputStream()));
			mixedInvalidUrlList = bufferedReader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Optional.ofNullable(bufferedReader).ifPresent(reader -> {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

	/**
	 * Genereate된 단축 URL의 중복률이 0.5퍼센트 미만일 경우 테스트 통과
	 * (전체 URL 리스트 갯수 / 중복된 단축URL 갯수) * 100 < 0.5
	 **/
	@Test
	public void generateShortenedUrlTest() {
		//중복을 제거한 단축URL 갯수
		long resultCount = urlList.stream()
				.map(ShorteningKeyHelper::generateShortenedUrl)
				.distinct()
				.count();

		long urlTotalCount = urlList.size();

		double rateDuplicated = ((urlTotalCount - resultCount + 1) / urlTotalCount) * 100.0;

		Assert.assertTrue(rateDuplicated < 0.5);

	}

	/**
	 * Url의 Schemes를 분리하는 메서드 테스트
	 **/
	@Test
	public void parseFromProtocolTest() {
		Assert.assertTrue(urlList.stream()
				.map(ShorteningKeyHelper::parseProtocolFromUrl)
				.allMatch(protocol -> protocol.equals("http") || protocol.equals("https")));
	}

	/**
	 * mixedWrongUrlListForTest.txt파일에는 유요하지않은 Url 5개 존재한다.
	 * 유효하지않은 Url의 갯수를 비교하여 테스트
	 **/
	@Test
	public void isValidUrlTest() {
		UrlValidator urlValidator = new UrlValidator();
		long count = mixedInvalidUrlList.stream()
				.filter(url -> !urlValidator.isValid(url))
				.count();

		Assert.assertTrue(count == 5);
	}

}
