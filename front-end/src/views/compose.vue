<template>
  <div class="container">
    <button class="close" title="Move to draft" @click="close()">close </button>
    <form enctype="multipart/form-data">
      <label>Subject</label>
      <div class="fix"></div>
      <input type="text" name="subject" id="sub" placeholder="subject" />
      <div class="contain">
        <div class="div1">
          <label>importance</label>
          <div class="fix"></div>
          <select id="selected" @change="onChange($event)">
            <option value="1">Urgent</option>
            <option value="2">Very Important</option>
            <option value="3">Important</option>
            <option value="4">Normal</option>
          </select>
          <label>To Send</label>
          <div class="fix"></div>
          <input
            id="toSend"
            type="text"
            name="send"
            placeholder="To send"
            v-model="txtInput"
          />
          <input list="contact" placeholder="Your Contact" @change="ADDLIST()" id="newContact"/>
          <datalist id="contact"  >
            
          </datalist>
          <input
            type="button"
            value="Add To Receivers"
            @click="addToReceivers()"
          />
          <form>
            
          </form>
        </div>
        <div class="div2">
          <div id="list"></div>
          <input type="button" value="Clear List" @click="clear()" />
        </div>
        <div class="fix"></div>
      </div>
      
      <input type="file" multiple id="myfiles" @change="selectfiles"/>
      <label>Message</label>
      <div class="fix"></div>
      <textarea
        name="message"
        id="message"
        cols="30"
        rows="5"
        placeholder="Message"
      >
      </textarea>

      <input type="button" value="Send" @click="send()" class="send" />
    </form>
  </div>
</template>
<script>
export default {
  name: "compose",
  props:{
    items: Object,
  },
  data() {
    return {
      checkState: false,
      txtInput: "",
      obj: {
        username: [],
        subject: "",
        body: "",
        importance: 1,
        type: "",
        attachment:[],
        files:new FormData(),
      },
      info:{},
      files: "",
      list:[],
      user:[],

    };
  },
  mounted(){
    console.log("here is the object "  + this.items);
    this.contact();
    this.load(); 
   
  },
  methods: {
    async contact(){
      await fetch("http://localhost:8085/getContact", {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.list = data;
        });

      var  elm = document.getElementById('contact');
      for (var i=0 ;i<this.list.length;i++)
      {
        var option = document.createElement('option'); 
        option.value = this.list[i]; 
        elm.appendChild(option)
      }
    },
    selectfiles(){
      var fileInput = document.querySelector("#myfiles");
      this.files = fileInput.files;
      for (let i = 0; i < this.files.length; i++) {
          this.obj.files.append("files", this.files[i]);
          this.obj.attachment.push(this.files[i].name);
          }
    },
    async close() {
      var textmsg = document.getElementById("message");
      this.obj.body = textmsg.value;
      var textsub = document.getElementById("sub");
      this.obj.subject = textsub.value;
      
     
      this.obj.type = "draft";
      this.obj.attachment=null;
          await fetch("http://localhost:8085/draft", {
          method: "post",
          body: JSON.stringify(this.obj),
        });
        alert("This email has been moved to the draft") ;
      this.$router.push({name: "signedIn"}) ;
    },
    load(){
      this.info = this.items ; 
      var subject = document.getElementById('sub') ; 
      var msg = document.getElementById('message');
      var select = document.getElementById('selected') ;
      subject.value = this.info.subject ;
      msg.value = this.info.body ; 
      select.value = this.info.importance ; 
      this.obj.username = this.info.username ; 
      this.listRecievers() ;
      
     
    },
    async send() {
      var textmsg = document.getElementById("message");
      this.obj.body = textmsg.value;
      var textsub = document.getElementById("sub");
      this.obj.subject = textsub.value;
      var fileInput = document.querySelector("#myfiles");
      //this.obj.files = fileInput.files;
      this.obj.type = "sent";
      //console.log(this.obj.body);
      //console.log(this.obj.subject);

      if (
        this.obj.body === "" ||
        this.obj.subject === "" ||
        this.obj.username.length === 0
      ) {
        alert("Email is not complete");
      } else {
        await fetch("http://localhost:8085/sent", {
          method: "post",
          body: JSON.stringify(this.obj),
        });
        console.log(this.obj.files);
        var username= this.obj.username;
        await fetch("http://localhost:8085/attachment", {
          method: "post",
          body: this.obj.files,
        });

        alert("Email has been sent successfully") ; 

        this.$router.push({name: 'signedIn'}); 
      }
      // console.log(this.obj.files);
    },
    ADDLIST(event){
     var x = document.getElementById('newContact') ;
     this.obj.username.push(x.value) ;
     this.listRecievers ();
     x.value = '' ;
    },
    onChange(event) {
      //console.log(event.target.value);
      this.obj.importance = event.target.value;

      this.obj.importance = parseInt(this.obj.importance);
    },
    clear() {
      this.obj.username = [];
      var listContainer = document.getElementById("list");
      listContainer.innerHTML = "";
    },
    listRecievers (){ 
      
        //console.log(this.username);

        var listData = this.obj.username;

        var listContainer = document.getElementById("list");
        listContainer.innerHTML = "";
        var listElement = document.createElement("ul");
        var listLabel = document.createElement("label");
        listLabel.innerHTML = "List of Receivers";
        listElement.appendChild(listLabel);
        listContainer.appendChild(listElement);

        var numberOfListItems = listData.length;
        var listItem;
        for (var i = 0; i < numberOfListItems; ++i) {
          listItem = document.createElement("li");
          listItem.innerHTML = listData[i];
          listElement.appendChild(listItem);
        }},
    async addToReceivers() {
      if (this.txtInput === "" || this.obj.username.includes(this.txtInput)) {
        return;
      }
        await fetch("http://localhost:8085/setting", {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
         .then((data) => {
          this.user = data ;
        });
        if(this.user[0] ==this.txtInput)
            {
            alert('You can not send Mail to your self')
           return ;
            }
      console.log("Here") ;
      await fetch("http://localhost:8085/" + this.txtInput, {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.checkState = data;
        });

      if (this.checkState === true) {
        this.obj.username.push(this.txtInput);
        this.listRecievers() ; 
      } else {
        alert("this user is unavailable.");
        return;
      }
    },

  },
};
</script>
<style scoped>
* {
  box-sizing: border-box;
}

.container {
  display: block;
  margin: auto;
  text-align: center;
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
  width: 50%;
  position: relative;
}
.div1,
.div2 {
  width: 40%;
  float: left;
}
.close {
  position: absolute;
  top: 0;
  right: 0;
}
#list {
  height: 100px;
  text-align: left;
  margin: 20px;
  background: #ddd;
  padding-top: 10px;
  overflow: auto;
  margin-top: 80px;
}
.div2 {
  margin-left: 50px;
}

label {
  float: left;
}
.fix {
  clear: both;
}

input[type="text"],
[type="email"],
input[list="contact"],
[type="button"],
input[type="file"],
textarea,
select,
.add {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
  resize: vertical;
}
input[value="Clear List"] {
  background: red;
  margin-left: 20px;
  width: 150px;
  color: #fff;
}
input[value="Add To Receivers"] {
  background: #5055e5;
  color: #fff;
}

.send {
  background: #5055e5;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.send:hover {
  background-color: #45a049;
}
</style>