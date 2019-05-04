
$(function () {
    Init();
});

let postid=null;
let arrayid=null;
let user = null;
let postnow = null;

let Init = async () =>{
    let response = await $.get('/finduser/abc');
    $("#userid").html(response.data.account);
    $("#username").html(response.data.name);
    $("#registerdate").html(response.data.created);

    user = response.data;

    CheckCount();
    FindTop();

    try{
        let list = await $.get('/listpost');
        list.data.sort();
        arrayid = list.data.length -1;
        for(let i=0 ; i < list.data.length ;i++){
            let contents = list.data[i];
            addLine(contents);
        }
    }catch (e) {
        $("#cotentlist").html(JSON.stringify(e))
    }
};

let CheckCount = async () => {
    let post = await $.get('/getcount/' + user.account);
    $("#countpost").html(post.data + "개");
};

let FindTopArrayId = async () => {
    let list = await $.get('/listpost');
    list.data.sort();
    arrayid = list.data.length -1;
};

let FindTop = async () => {
    let get = await $.get('/get/' + user.account);
    WriteContent(get.data);
    postid = get.data.id;
    FindTopArrayId();
};

let addLine = (response) => {
    $("#cotentlist").append(
        `<button id="${response.id}" class="looklikelink" onclick="ClickContent(${response.id})"><li>${response.title}</li></button>`
    );
};

let ClickContent = async (id) => {
    let post = await $.get('/view/' + id);
    WriteContent(post.data);
    postid = id;
    FindArrayId(id);
};

let FindArrayId = async (id) =>{
    let response = await $.get('/listpost');
    for(let i=0 ; i < response.data.length ;i++){
        if(id === response.data[i].id){
            arrayid = i;
        }
    }
};

let WriteContent = async (response) => {
    $("#mainpost_title").html(response.title);
    $("#mainpost_author").html(user.name);
    $("#mainpost_content").html(response.content);
    $("#mainpost_date").html(response.updated);
    postnow = response;
};


let PageUpDown = async (button) => {
    let response = await $.get('/listpost');
    if ($(button).text() === '이전'){
        if (arrayid === 0){
            alert("첫 페이지입니다.");
            return ;
        }
        arrayid = arrayid -1;
        PostContent(response);
    }else if($(button).text() === '다음'){
        if(arrayid === response.data.length - 1){
            alert("마지막 페이지입니다.");
            return ;
        }
        arrayid = arrayid + 1;
        PostContent(response);
    }
};


let PostContent = (response) => {
    let data = response.data[arrayid];
    postid = data.id;
    WriteContent(data);
};

let AddContent = () => {
    $("#title").val('');
    $("#contents").val('');
    $("#addbtn").text('추가');
    $("#container").show(100);
};

let Close = () => {
    $("#container").hide(100);
};

let  Add = async () => {
  let title = $("#title").val().trim();
  let contents = $("#contents").val().trim();
  let btn = $("#addbtn").text();
  if (btn === '추가') {
      let data = {
          account : user.account,
          title: title,
          content : contents
      };

      let responses = await $.ajax({
          url: '/addpost',
          type:'post',
          contentType:'application/json',
          dataType:'json',
          data:JSON.stringify(data)
      });

      addLine(responses.data);
      FindTop();
      CheckCount();
  }else if (btn === '수정'){
     let data = {
          title: title,
          content : contents
      };

      let responses = await $.ajax({
          url: '/updatepost/' + postid,
          type:'put',
          contentType:'application/json',
          dataType:'json',
          data:JSON.stringify(data)
      });
      WriteContent(responses.data);
      FindArrayId(postid);
  }
    Close();
};

let Delete = async () => {
    try{
        if(confirm("삭제하시겠습니까?") === true){
            let response = await $.ajax({
                url: '/deletepost/' + postid,
                type:'delete'
            });
            if(response.code === 202){
                FindTop();
                $(`#${postid}`).remove();
                CheckCount();
            }else{
                alert("삭제실패");
            }
        }
    }catch (e) {
        console.log(JSON.stringify(e));
    }
};

let Update = async () => {
    AddContent();
    $("#title").val(postnow.title);
    $("#contents").val(postnow.content);
    $("#addbtn").text('수정');
};