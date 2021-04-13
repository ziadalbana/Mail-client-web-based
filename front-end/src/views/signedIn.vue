<template >
  <b-container fluid style="background-color: #3323; height: 100vh">
    <!-- User Interface controls -->
    <b-row>
      <div
        class="imgg"
        style="
          text-align: center;
          font-size: 70px;
          width: 100%;
          color: white;
          font-weight: bolder;
          text-decoration-style: wavy;
          text-decoration-style: solid;
          height: 250px;
          background-size: cover;
          background-position: center;
          margin-bottom: 20px;
        "
      ></div>
      <b-col sm="9" offset="2" class="welcome"> </b-col>
      <div class="cont">
        <b-button variant="secondary" @click="goSettings">Settings</b-button>
        <b-button variant="danger" @click="goSignin">Log out</b-button>
      </div>

      <hr />

      <div class="filter-container" style="width: 100%; text-align: left">
        <span style="font-weight: bolder; margin-left: 20px"> Filter </span>
        <b-form-select
          style="
            width: 12%;
            margin: 0px 20px;
            height: 50px;
            paddint-top: 25px;
            background-color: #416098;
            color: white;
            font-size: 20px;
          "
          v-model="filterselected"
          :options="filteroptions"
          class="mb-3"
          value-field="item"
          text-field="name"
          disabled-field="notEnabled"
        ></b-form-select>
        <b-form-input
          v-model="filterword"
          placeholder="Type to filter"
          style="
            display: inline-block;
            width: 15%;
            padding-top: 25px;
            height: 50px;
            font-size: 18px;
          "
        ></b-form-input>
        <b-button variant="success" style="margin-left: 20px" @click="filterFun"
          >Filter</b-button
        >
        <h1 style="display: inline; margin: 0px 5px">|</h1>
        <span style="font-weight: bolder"> Sort </span>

        <b-form-select
          style="
            width: 12%;
            margin: 0px 20px;
            height: 50px;
            font-size: 18px;
            background-color: #416098;
            color: white;
            font-size: 20px;
          "
          v-model="sortselected"
          :options="sortoptions"
          class="mb-3"
          value-field="item"
          text-field="name"
          disabled-field="notEnabled"
        ></b-form-select>
        <b-button variant="success" @click="sortFun">Sort</b-button>
        <h1 style="display: inline; margin: 0px 5px">|</h1>
        <span style="font-weight: bold"> Search </span>
        <b-form-input
          style="
            height: 40px;
            margin: 0px 20px;
            font-size: 18px;
            width: 15%;
            display: inline-block;
          "
          id="filter-input"
          v-model="search"
          type="search"
          placeholder="Type to Search"
        ></b-form-input>

        <b-button
          class="gbtn search-btn"
          id="inbtn"
          variant="success"
          @click="searchFun"
          >Search</b-button
        >
      </div>

      <b-col>
        <div class="line">
          <hr style="width: 100%" />
        </div>
      </b-col>

      <br />
      <div class="line">
        <hr style="width: 100%" />
      </div>
      <div
        class="mt-3 btns"
        style="
          width: 100%;
          position: relative;
          padding: 20px;
          background-color: #333;
        "
      >
        <b-button variant="danger" class="sendbtn" @click="sendEmail"
          >Send email</b-button
        >
        <b-button-group style="height: 50px">
          <b-button class="gbtn" id="inbtn" variant="primary" @click="getInbox"
            >Inbox</b-button
          >
          <b-button class="gbtn" variant="danger" @click="getSent"
            >Sent</b-button
          >
          <b-button class="gbtn" variant="secondary" @click="getTrash"
            >Trash</b-button
          >
          <b-button class="gbtn" variant="success" @click="getDraft"
            >Draft</b-button
          >
        </b-button-group>
        <div style="display: inline-block; position: absolute">
          <b-button
            class="gbtn"
            variant="danger"
            @click="goContact"
            style="margin-left: 250px"
            >Contacts</b-button
          >
        </div>
      </div>

      <div
        class="emails"
        style="
          margin: auto;
          background-color: #333;
          width: 100%;
          color: white;
          margin-top: 0px !important;
        "
      >
        <h2 class="emails-title">{{ name }}</h2>
      </div>

      <!-- Main table element -->
      <b-table
        class="table"
        :items="items"
        :fields="fields"
        :current-page="currentPage"
        :per-page="perPage"
        :filter="filter"
        :filter-included-fields="filterOn"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        :sort-direction="sortDirection"
        stacked="md"
        show-empty
        small
        @filtered="onFiltered"
        style="font-weight: bolder; background-color: #3323; min-height: 50vh"
      >
        <template #cell(name)="row">
          {{ row.value.first }} {{ row.value.last }}
        </template>

        <template #cell(actions)="row">
          <b-button
            v-if="name != `Draft`"
            size="sm"
            @click="
              getEmail(row.index), info(row.item, row.index, $event.target)
            "
            class="mr-1"
          >
            Show Email
          </b-button>
          <b-button
            v-else
            size="sm"
            @click="
              getDraftInfo(row.index), info(row.item, row.index, $event.target)
            "
            class="mr-1"
          >
            Edit Email
          </b-button>

          <b-button
            variant="danger"
            size="sm"
            class="bt"
            @click="showMsgBoxTwo(row.index)"
          >
            X</b-button
          >
        </template>
      </b-table>
    </b-row>
    <!-- Info modal -->
    <b-modal
      :id="infoModal.id"
      @hide="resetInfoModal"
      size="xl"
      ok-only
      header-bg-variant="dark"
      body-bg-variant="dark"
      footer-bg-variant="dark"
      :ok-disabled="cmpShow"
      style="background-image: url(../photos/1234.jpg)"
    >
      <template v-if="name != 'Draft' && !cmpShow">
        <div class="info" style="background-image: url(../photos/1234.jpg)">
          <div style="text-align: center">
            <h1 style="color: white; font-size: 50px">Email contenct</h1>
          </div>

          <hr class="my-2" />
          <label class="lbl"> Username: </label>
          <span class="infoSpan"> {{ emailInfo.username[0] }} </span> <br />
          <hr class="my-2" />
          <label class="lbl"> Importance: </label>
          <span class="infoSpan"> {{ emailInfo.importance }} </span>
          <br />
          <hr class="my-2" />
          <label class="lbl"> Date: </label>
          <span class="infoSpan"> {{ emailInfo.date }} </span> <br />
          <hr class="my-2" />
          <label class="lbl">Subject: </label>
          <span class="infoSpan"> {{ emailInfo.subject }} </span> <br />
          <hr class="my-2" />
          <label class="lbl">Type: </label>
          <span class="infoSpan"> {{ emailInfo.type }} </span> <br />
          <hr class="my-4" />

          <h2 style="color: white">The message</h2>

          <hr class="my-2" style="width: 30%" />

          <span class="infoSpan">
            {{ emailInfo.body }}
          </span>
          <hr class="my-4" />

          <h2 style="color: white">Attachments</h2>
          <ol>
            <li
              style="color: white"
              v-for="(att, index) in attachments"
              :key="index"
            >
              <a
                style="color: white; font-weight: bolder"
                href="#/signedIn"
                @click="getByte(index)"
                >{{ attachments[index] }}</a
              >
            </li>
          </ol>
          <hr class="my-2" style="width: 30%" />
        </div>
      </template>
      <template v-if="cmpShow">
        <composeCMP :items="emailInfo" />
      </template>
    </b-modal>
  </b-container>
</template>

<script>
import composeCMP from "../components/composeCMP.vue";
export default {
  //eslint-disable-next-line no-unused-vars
  components: { composeCMP },
  data() {
    return {
      contacts: ["khalid", "ahmed", "lolo"],
      currentUser:"",
      checkState: false,
      txtInput: "",
      obj: {
        username: [],
        subject: "",
        body: "",
        importance: 1,
        type: "",
        files: "mahy",
      },
      emailInfo: {
        username: [],
        date: "",
        subject: "",
        body: "",
        importance: "",
        type: "",
        attachment: [],
        attachmentPath: [],
      },
      attachments: [],
      attachmentsPath: [],
      infoModal: {
        id: "info-modal",
        title: "",
        content: "",
      },
      search: "",
      items: [],
      fields: [
        {
          key: "userName",
          label: "Email",
          sortDirection: "desc",
        },
        {
          key: "subject",
          label: "Subject",
          class: "text-center",
        },
        {
          key: "importance",
          label: "importance",
          class: "text-center",
        },
        { key: "date", label: "Date", class: "text-center" },

        { key: "actions", label: "Actions" },
      ],
      totalRows: 1,
      currentPage: 1,
      perPage: 100,
      sortBy: "",
      sortDesc: false,
      sortDirection: "asc",
      filter: null,
      filterword: "",
      filterOn: [],
      name: "Inbox",
      boxTwo: "",
      sortselected: null,
      filterselected: null,
      sortoptions: [
        { item: null, name: "choose one", notEnabled: true },
        { item: "username", name: "username" },
        { item: "date", name: "date" },
        { item: "subject", name: "subject" },
        { item: "importance", name: "importance" },
      ],
      filteroptions: [
        { item: null, name: "choose one", notEnabled: true },
        { item: "username", name: "username" },
        { item: "subject", name: "subject" },
      ],
      cmpShow: false,
      byte: [],
      attachmentdownload: {
        name: "",
        path: "",
      },
      attachmentbyte: {
        myfile: "",
        file: [],
      },
    };
  },
  computed: {
    sortOptions() {
      // Create an options list from our fields
      return this.fields
        .filter((f) => f.sortable)
        .map((f) => {
          return { text: f.label, value: f.key };
        });
    },
  },
  mounted() {
    // Set the initial number of items
    this.items = this.getInbox();
    this.totalRows = this.items.length;
  },

  methods: {
    async getByte(index) {
      // fetch here
      this.attachmentdownload.name = this.attachments[index];
      this.attachmentdownload.path = this.attachmentsPath[index];
      await fetch("http://localhost:8085/download", {
        method: "post",
        mode: "cors",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(this.attachmentdownload),
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.attachmentbyte = data;
        });
      const byteCharacters = atob(this.attachmentbyte.file);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      var byteArray = new Uint8Array(byteNumbers);
      var a = window.document.createElement("a");
      a.href = window.URL.createObjectURL(
        new Blob([byteArray], { type: "application/octet-stream" })
      );
      a.download = this.attachmentbyte.myfile;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    },

    async getBody(response) {
      var ret = await response.json();
      return ret;
    },
    goContact() {
      this.$router.push({ name: "contacts" });
    },
    async getDraftInfo(index) {
      await this.getEmail(index);
      await this.deldraft(index);
      this.cmpShow = true;
    },

    async getEmail(index) {
      var date = this.items[index].date;
      console.log(date);
      // fetch here
      await fetch("http://localhost:8085/show/" + date, {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.emailInfo = data;
        });
      this.attachments = this.emailInfo.attachment;
      this.attachmentsPath = this.emailInfo.attachmentPath;
      console.log("email info ", this.emailInfo);
    },

    async filterFun() {
      await fetch(
        "http://localhost:8085/getEmails" +
          "/" +
          this.name +
          "/" +
          this.filterselected +
          "/" +
          this.filterword,
        {
          method: "GET",
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.items = data;
        });
      this.edit();
    },
    async sortFun() {
      await fetch(
        "http://localhost:8085/sort/" + this.name + "/" + this.sortselected,
        {
          method: "GET",
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.items = data;
        });
      this.edit();
    },
    info(item, index, button) {
      this.infoModal.title = `Row index: ${index}`;
      this.infoModal.content = JSON.stringify(item, null, 2);
      this.$root.$emit("bv::show::modal", this.infoModal.id, button);
    },
    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },
    onFiltered(filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
    showMsgBoxTwo(index) {
      this.boxTwo = "";
      this.$bvModal
        .msgBoxConfirm("Please confirm that you want to delete this email.", {
          title: "Please Confirm",
          size: "sm",
          buttonSize: "sm",
          okVariant: "danger",
          okTitle: "YES",
          cancelTitle: "NO",
          footerClass: "p-2",
          hideHeaderClose: false,
          centered: true,
        })
        .then((value) => {
          this.boxTwo = value;
          if (this.boxTwo == true) {
            this.del(index);
          }
          this.boxTwo = "";
        })
        .catch((err) => {
          // An error occurred
        });
    },
    async del(index) {
      var date = this.items[index].date;
      //fetch
      await fetch("http://localhost:8085/" + date, {
        method: "delete",
      });
      this.items.splice(index, 1);
      this.edit();
    },
    async deldraft(index) {
      var date = this.items[index].date;
      //fetch
      await fetch("http://localhost:8085/deleteDraft/" + date, {
        method: "delete",
      });
      this.edit();
      this.items.splice(index, 1);
    },
    async getInbox() {
      this.name = "Inbox";
      await fetch("http://localhost:8085/getEmails/inbox", {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.items = data;
        });
      this.edit();
      return this.items;
    },
    async getSent() {
      this.name = "Sent";
      await fetch("http://localhost:8085/getEmails/sent", {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.items = data;
        });
      this.edit();
      return this.items;
    },
    async getDraft() {
      this.name = "Draft";
      await fetch("http://localhost:8085/getEmails/draft", {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.items = data;
        });
      this.edit();
      return this.items;
    },
    async getTrash() {
      this.name = "Trash";
      await fetch("http://localhost:8085/getEmails/trash", {
        method: "GET",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.items = data;
        });
      this.edit();
      return this.items;
    },
    async searchFun() {
      console.log(this.search);
      await fetch("http://localhost:8085/search/", {
        method: "post",
        //headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ word: this.search }),
      })
        .then((respone) => {
          return respone.json();
        })
        .then((data) => {
          this.items = data;
        });
      this.edit();
    },
    edit() {
      for (var i = 0; i < this.items.length; i++) {
        this.items[i].userName = this.items[i].userName[0];
      }
    },
    sendEmail() {
      this.$router.push({ name: "compose" });
    },
    goSettings() {
      this.$router.push({ name: "settings" });
    },
    async goSignin() {
      console.log(123);
      await fetch('http://localhost:8085/reset',{
        method:'get',
      }),
      alert("logged out successfully. ");
      this.$router.push({ name: "signIn" });
    },
  },
};
</script>

<style scoped>
.imgg {
  background-image: url(../photos/123.jpg);
}
.infoSpan {
  color: black;
  font-weight: bolder;
  font-size: 25px;
}
.bt {
  margin-left: 20px;
}
.slider {
  display: block;
  margin: auto;
  margin-bottom: 15px !important;
}
.icons a {
  margin: 0px 5px;
}
.welcome h1 {
  font-size: 70px !important;
}
.table {
  margin-top: 20px;
}
.btns {
  margin: auto;
}
.gbtn {
  margin: 0px 10px;
}
.search-container {
  margin: 20px;
}
.search-btn {
  margin: 10px;
}
.line {
  width: 80%;
  margin: auto;
}
.emails {
  margin-top: 20px !important;
}
.emails .emails-title {
  font-size: 50px;
}
.box {
  margin-left: 260px;
}
.display-3 {
  text-align: center;
}
.lbl {
  font-weight: 700;
  font-size: 25px;
  color: white;
}
span {
  font-size: 20px;
}
.info h2 {
  text-align: center;
}
.info h4 {
  text-align: center;
}
.sendbtn {
  position: absolute;
  left: 200px;
}
.cont {
  margin: 10px 10px;
  width: 90%;
  text-align: right;
}
.btn {
  margin: 0px 5px;
}

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

.fix {
  clear: both;
}

input[type="text"],
input[type="email"],
input[type="button"],
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
.hide {
  display: none;
}
.sort-container {
  width: 100%;
}
.modal-footer .btn {
  background-color: red;
}

.bg-dark .btn {
  background-color: red;
}
.close {
  display: none;
}
</style>