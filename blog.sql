INSERT INTO public.role VALUES (1, 'ADMIN');
INSERT INTO public.role VALUES (2, 'AUTHOR');

INSERT INTO public.account (id, email, is_active, password, username) VALUES (1, 'email@email.com', true, '$2a$12$MCeylXMYogbtGjRc/HZ06uJ/3KmBWCxW93sYe.fKkK5V/k/7mCewa', 'admin');
INSERT INTO public.account (id, email, is_active, password, username) VALUES (4, 'e@e.com', true, '$2a$12$e0WxEoaHVGH5eRNwD9.hb.wFiywoiyAttBqFTcV3ytHvsQ8Vglvee', '12345');
INSERT INTO public.account (id, email, is_active, password, username) VALUES (6, 'r@r.c', true, '$2a$12$lH7G9SzXZhOEnePJyqfV/O..TDk1k.Mhrd1vGdwVaKdVLp/4C5cjm', '123');
INSERT INTO public.account (id, email, is_active, password, username) VALUES (7, 'nhatkap2@gmail.com', true, '$2a$12$jreTvlfVbz7I7L0w.gpyE.1GU4ivnU5o0F6gfmamaW6wW0EVIYQRK', 'hdkdjd');
INSERT INTO public.account_role (account_id, role_id) VALUES (1, 1);
INSERT INTO public.account_role (account_id, role_id) VALUES (1, 2);
INSERT INTO public.account_role (account_id, role_id) VALUES (4, 2);
INSERT INTO public.account_role (account_id, role_id) VALUES (6, 2);
INSERT INTO public.account_role (account_id, role_id) VALUES (7, 2);
INSERT INTO public.author (id, avatar_url, code, description, name, account_id) VALUES (6, '1564027938386-author.jpg', 'sfjdlfk', 'jklkjjk', 'dajkf', 6);
INSERT INTO public.author (id, avatar_url, code, description, name, account_id) VALUES (1, '1564028012736-author.jpg', 'dounyat', 'Live in Hanoi. Taiping is my motherland', 'Do Dinh Nhat', 1);
INSERT INTO public.author (id, avatar_url, code, description, name, account_id) VALUES (7, '1564163480195-author.jpg', 'điownjdk', 'jdhdjdnfbd', 'dhkndfb', 7);
INSERT INTO public.author (id, avatar_url, code, description, name, account_id) VALUES (4, '1564544390234-author.jpg', 'ding-nyat', 'I’m a software engineer with a passion for REST, TDD and clean code, Web Security and Data Mining. Baeldung is about all of these and more.', 'Ding Nyat', 4);
INSERT INTO public.category (id, code, description, name) VALUES (1, 'spring-framework', 'Spring Framework is based on Java Servlet....', 'Spring Framework');
INSERT INTO public.category_series (category_id, series_id) VALUES (1, 1);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (1, 'Huang', 'Thanks. I really like your post', '2019-07-30 13:38:57.331000', true, false, 2, null);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (3, 'Dingnyat', 'Thank a lots', '2019-07-30 22:43:45.390000', true, false, null, 1);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (4, 'Khung', 'Thanks', '2019-07-30 22:51:30.843000', true, false, null, 1);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (6, 'Chi', 'Thanks con kak', '2019-07-30 22:53:02.066000', true, false, 2, null);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (5, 'Chi', 'Thanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kak', '2019-07-30 22:53:02.066000', true, false, null, 3);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (7, 'Chi', 'Thanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kak', '2019-07-30 22:53:02.066000', true, false, null, 5);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (8, 'Chi', 'Thanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kakThanks con kak', '2019-07-30 22:53:02.066000', true, false, null, 7);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (9, 'Huang', 'Thanks. I really like your post', '2019-07-30 13:38:57.331000', true, false, 2, null);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (10, 'Huang', 'Thanks. I really like your post', '2019-07-30 13:38:57.331000', true, false, 2, null);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (11, 'Huang', 'Thanks. I really like your post', '2019-07-30 13:38:57.331000', true, false, null, 9);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (14, 'Trung', 'Really?', '2019-07-31 08:08:12.875000', false, false, null, 10);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (15, 'Huang', 'yes. you are an idiot', '2019-07-31 08:08:49.427000', false, false, null, 14);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (16, 'KOK', 'you are ass hole', '2019-07-31 08:09:19.627000', false, false, null, 14);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (17, 'KOK', 'Hmm', '2019-07-31 08:09:36.318000', false, false, null, 10);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (18, 'Thương', 'Hay quá anh ơi', '2019-07-31 08:36:12.759000', false, false, 2, null);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (19, 'Loan', 'Hay cái gì mà hay?', '2019-07-31 09:24:21.089000', false, false, null, 18);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (20, 'An', 'Bạn bị đui à =))', '2019-07-31 09:24:45.392000', false, false, null, 19);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (21, 'Loan', 'Đui cc', '2019-07-31 09:25:32.922000', false, false, null, 20);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (22, 'Thương', 'Nó bị đui đó anh :v', '2019-07-31 09:25:57.109000', false, false, null, 20);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (23, 'An', 'Ko đui chắc mù', '2019-07-31 09:26:25.936000', false, false, null, 21);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (24, 'Hà', 'Hmm cc', '2019-07-31 09:36:12.499000', false, false, null, 17);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (25, 'KOK', 'Wat?', '2019-07-31 09:37:05.809000', false, false, null, 24);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (26, 'Hà', 'Khong có gì', '2019-07-31 09:37:42.151000', false, false, null, 25);
INSERT INTO public.comment (id, comment_by, content, created_date, is_accepted, is_removed, post_id, parent_comment_id) VALUES (27, 'Nam', 'WTF?', '2019-07-31 09:38:26.077000', false, false, null, 24);
INSERT INTO public.persistent_login (series, last_used, token, username) VALUES ('uSMBoDN+IsHOposT/eNHeA==', '2019-07-25 01:10:58.970000', '9KTF/tngw0I7KvuWLT4ouA==', '123');
INSERT INTO public.post (id, code, content, created_date, is_actived, is_comment_blocked, last_modified_date, position_in_series, title, author_id, series_id, last_modified_by, summary) VALUES (12, 'fjsd', '<p>trerter</p>
', '2019-07-25 00:29:34.990000', true, false, '2019-07-25 01:29:45.836000', 12, 'kjfsdlk', 6, 5, 1, null);
INSERT INTO public.post (id, code, content, created_date, is_actived, is_comment_blocked, last_modified_date, position_in_series, title, author_id, series_id, last_modified_by, summary) VALUES (11, 'sdfs', '<p>dsfsdfsd</p>
', '2019-07-24 23:32:10.700000', true, false, '2019-07-25 01:30:23.170000', 6, 'sdff', 6, 1, 1, null);
INSERT INTO public.post (id, code, content, created_date, is_actived, is_comment_blocked, last_modified_date, position_in_series, title, author_id, series_id, last_modified_by, summary) VALUES (2, 'freemarker-in-spring-mvc-tutorial', '<h2><strong>1. Overview</strong></h2>

<p><strong>FreeMarker</strong> is a Java based template engine from the Apache Software Foundation. Like other template engines, FreeMarker is designed to support HTML web pages in applications following the MVC pattern. This tutorial illustrates how to <strong>configure FreeMarker for use in Spring MVC</strong> as an alternative to JSP.</p>

<div class="code-block code-block-5" style="clear:both; margin-bottom:8px; margin-left:8px; margin-right:8px; margin-top:8px"><span></span></div>

<p>The article will not discuss the basics of Spring MVC usage. For an in-depth look at that, please refer to <a href="https://www.baeldung.com/spring-mvc-tutorial">this article</a>. Additionally, this is not intended to be a detailed look at FreeMarker&rsquo;s extensive capabilities. For more information on FreeMarker usage and syntax, please visit <a href="http://freemarker.incubator.apache.org/">its website</a>.</p>

<h2><strong>2. Maven Dependencies</strong></h2>

<p>Since this is a Maven-based project, we first add the required dependencies to the <em>pom.xml</em>:</p>

<pre>
<code class="language-xml">&lt;dependency&gt;
    &lt;groupId&gt;org.freemarker&lt;/groupId&gt;
    &lt;artifactId&gt;freemarker&lt;/artifactId&gt;
    &lt;version&gt;2.3.23&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.springframework&lt;/groupId&gt;
    &lt;artifactId&gt;spring-context-support&lt;/artifactId&gt;
    &lt;version&gt;${spring.version}&lt;/version&gt;
&lt;/dependency&gt;</code></pre>

<h2><strong>3. Configurations</strong></h2>

<p>Now let&rsquo;s dive into the configuration in to project. This is an annotation-based Spring project, so we will not demonstrate the XML-based configuration.</p>

<h3><strong>3.1. Spring Web Configuration</strong></h3>

<p>Let&rsquo;s create a class to configure the web components. For that we need to annotate the class with <em>@EnableWebMvc</em>, <em>@Configuration</em> and <em>@ComponentScan</em>.</p>

<div>
<pre>
<code class="language-java">@EnableWebMvc
@Configuration
@ComponentScan({"com.baeldung.freemarker"})
public class SpringWebConfig extends WebMvcConfigurerAdapter {
    // All web configuration will go here.
}</code></pre>

<p>&nbsp;</p>
</div>

<h3><strong>3.2. Configure <em>ViewResolver</em></strong></h3>

<p>Spring MVC Framework provides the <strong><em>ViewResolver</em> </strong>interface, that maps view names to actual views. We will create an instance of <em><strong>FreeMarkerViewResolver</strong></em>, which belongs to the <strong>spring-webmvc</strong> dependency.</p>

<p>That object needs to be configured with the required values that will be used at run-time. For example, we will configure the view resolver to use FreeMarker for views ending in <em><strong>.ftl</strong></em>:</p>

<div>
<pre>
<code class="language-java">@Bean
public FreeMarkerViewResolver freemarkerViewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(true);
    resolver.setPrefix("");
    resolver.setSuffix(".ftl");
    return resolver;
}
</code></pre>

<p>&nbsp;</p>
</div>

<p>Also, notice how we can also control the caching mode here &ndash; this should only be disabled for debugging and development.</p>

<h3><strong>3.3. FreeMarker Template Path Configuration</strong></h3>

<p>Next, we will set the template path, which indicates where the templates are located in the web context:</p>

<pre>
<code class="language-java">@Bean
public FreeMarkerConfigurer freemarkerConfig() {
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/ftl/");
    return freeMarkerConfigurer;
}
</code></pre>

<h3><strong>3.4. Spring Controller Configuration</strong></h3>

<p>Now we can use a Spring Controller to <strong>process a FreeMarker template for display</strong>. This is simply a conventional Spring Controller:</p>

<div>
<pre>
<code class="language-java">@RequestMapping(value = "/cars", method = RequestMethod.GET)
public String init(@ModelAttribute("model") ModelMap model) {
    model.addAttribute("carList", carList);
    return "index";
}
</code></pre>

<p>&nbsp;</p>
</div>

<p>The <em>FreeMarkerViewResolver</em> and path configurations defined previously will take care of translating the view name <em>index</em> to the proper FreeMarker view.</p>

<h2><strong>4. FreeMarker HTML Template</strong></h2>

<h3><strong>4.1. Create Simple HTML Template View</strong></h3>

<p>It is now time to create a <strong>HTML template with FreeMarker</strong>. In our example, we added a list of cars to the model. FreeMarker can access that list and display it by iterating over its contents.</p>

<p>When a request is made for the <em>/cars</em> URI, Spring will process the template using the model that it is provided. In our template, the <strong><em>#list</em> directive</strong> indicates that FreeMarker should loop over the <em>carList</em> object from the model, using <em>car</em> to refer to the current element, and render the content within that block.</p>

<div class="code-block code-block-6" style="clear:both; margin-bottom:8px; margin-left:8px; margin-right:8px; margin-top:8px"><span></span></div>

<p>The following code also includes <strong>FreeMarker</strong><strong> expressions</strong> to refer to the attributes of each element in <em>carList</em>; or example, to display the current car element&rsquo;s <em>make</em> property, we use the expression <em>${car.make}</em>.</p>

<div>
<pre>
<code class="language-html">&lt;div id="header"&gt;
  &lt;h2&gt;FreeMarker Spring MVC Hello World&lt;/h2&gt;
&lt;/div&gt;
&lt;div id="content"&gt;
  &lt;fieldset&gt;
    &lt;legend&gt;Add Car&lt;/legend&gt;
    &lt;form name="car" action="add" method="post"&gt;
      Make : &lt;input type="text" name="make" /&gt;&lt;br/&gt;
      Model: &lt;input type="text" name="model" /&gt;&lt;br/&gt;
      &lt;input type="submit" value="Save" /&gt;
    &lt;/form&gt;
  &lt;/fieldset&gt;
  &lt;br/&gt;
  &lt;table class="datatable"&gt;
    &lt;tr&gt;
      &lt;th&gt;Make&lt;/th&gt;
      &lt;th&gt;Model&lt;/th&gt;
    &lt;/tr&gt;
    &lt;#list model["carList"] as car&gt;
      &lt;tr&gt;
        &lt;td&gt;${car.make}&lt;/td&gt;
        &lt;td&gt;${car.model}&lt;/td&gt;
      &lt;/tr&gt;
    &lt;/#list&gt;
  &lt;/table&gt;
&lt;/div&gt;
</code></pre>

<p>&nbsp;</p>
</div>

<p>After styling the output with CSS, the processed FreeMarker template generates a form and list of cars:</p>

<p><a href="https://www.baeldung.com/wp-content/uploads/2016/07/browser_localhost-300x235-1.png" rel="attachment wp-att-4896"><img alt="browser_localhost-300x235" class="alignnone size-medium wp-image-4896" src="https://www.baeldung.com/wp-content/uploads/2016/07/browser_localhost-300x235-1.png" style="height:235px; width:300px" /></a></p>

<h2><strong>5. Conclusion </strong></h2>

<p>In this article, we discussed the how to integrate<strong> FreeMarker in a Spring MVC application. </strong>FreeMarker&rsquo;s capabilities go far beyond what we demonstrated, so please visit the <a href="http://freemarker.incubator.apache.org/">Apache FreeMarker website</a> for more detailed information on its use.</p>

<p>The sample code in this article is available in a project on <a href="https://github.com/eugenp/tutorials/tree/master/spring-freemarker" rel="noopener" target="_blank">Github</a>.</p>
', '2019-07-24 22:51:28.373000', true, false, '2019-07-27 03:05:52.210000', 32, 'Introduction to Using FreeMarker in Spring MVC', 4, 1, 1, null);
INSERT INTO public.post_tag (post_id, tag_id) VALUES (12, 1);
INSERT INTO public.post_tag (post_id, tag_id) VALUES (11, 1);
INSERT INTO public.post_tag (post_id, tag_id) VALUES (2, 15);
INSERT INTO public.post_tag (post_id, tag_id) VALUES (2, 16);
INSERT INTO public.role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role (id, name) VALUES (2, 'ROLE_AUTHOR');
INSERT INTO public.series (id, code, description, name) VALUES (1, 'spring-from-zero', 'Learn Spring From Zero......', 'Spring From Zero');
INSERT INTO public.series (id, code, description, name) VALUES (5, 'basic-java', 'Learning Basic Java', 'Basic Java');
INSERT INTO public.social_link (id, link, name, author_id) VALUES (6, 'https://www.facebook.com/larry.dou.90', 'Facebook', 1);
INSERT INTO public.social_link (id, link, name, author_id) VALUES (7, 'https://twitter.com/', 'Twitter', 1);
INSERT INTO public.social_link (id, link, name, author_id) VALUES (10, '//facebook.com', 'Faceboook', 4);
INSERT INTO public.social_link (id, link, name, author_id) VALUES (11, '//twitter.com', 'Twitter', 4);
INSERT INTO public.tag (id, code, name, category_id) VALUES (1, 'spring-thymeleaf', 'Thymeleaf', 1);
INSERT INTO public.tag (id, code, name, category_id) VALUES (15, 'freemarker', 'Freemarker', 1);
INSERT INTO public.tag (id, code, name, category_id) VALUES (16, 'spring-security', 'Spring Security', 1);