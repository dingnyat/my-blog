<#include "./layout/layout.ftl"/>

<#macro customResources>
  <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
  <style>
    #my-quote {
      padding: 10px;
      margin: 10px auto;
      font-style: italic;
      color: #555555;
      border-left: 8px solid #28a745;
      position: relative;
      background: #cdcdcd;
    }

    #my-quote cite {
      font-family: 'Varela Round', sans-serif;
      font-style: normal;
      font-weight: bold;
    }
  </style>
</#macro>
<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5">
    <div class="row d-flex justify-content-center align-items-center">
      <div class="col-sm-4 col-12">
        <div id="avatar" class="p-lg-5 p-md-4 p-3">
          <img class="rounded-circle img-thumbnail mx-auto d-block img-fluid" src="<@s.url '/img/avatar.png'/>"
               alt="avatar"
               width="350px" height="350px">
        </div>
        <blockquote id="my-quote">
          <p>Người có tính hay thay đổi có thay đổi được tính hay thay đổi của họ không nhỉ?</p>
          <cite>Hmm...</cite>
        </blockquote>
      </div>
      <div class="col-sm-8 col-12">
        <h5 class="display-4 mb-5">Thông tin website</h5>
        <div id="intro-para">
          <p>
            Thôi có đọc thì các bạn cũng có nhớ mấy thông tin này đâu =)) Nên lười viết lắm.
          </p>
          <p>Nhớ mỗi việc mình tên là <strong>Đình Nhất</strong> là được <3 </p>
        </div>
        <div id="contact">
          <h5 class="display-5">Follow me</h5>
          <div id="social-link">
            <a href="https://www.facebook.com/dingnyat">
              <img class="img-thumbnail" src="<@s.url '/img/facebook.png'/>" width="40px" height="40px" alt="facebook">
            </a>
            <a href="https://twitter.com/dingnyat">
              <img class="img-thumbnail" src="<@s.url '/img/twitter.png'/>" width="40px" height="40px" alt="twitter">
            </a>
            <a href="https://github.com/dingnyat">
              <img class="img-thumbnail" src="<@s.url '/img/github.png'/>" width="40px" height="40px" alt="github">
            </a>
          </div>
        </div>
      </div>
    </div>
  </section>
</#macro>

<@displayPage page_title="Giới thiệu"/>
