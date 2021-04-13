<template>
  <b-container fluid style="background-color: #3323; height: 100vh">
    <!-- User Interface controls -->
    <b-row>
      <b-col sm="9" offset="2" class="welcome">
        <hr />
        <h1>Contacts</h1>
        <hr />
      </b-col>
      <hr />
      <div style="width: 100%; margin: auto">
        <b-button variant="danger" @click="goBack" style="margin-right: 0px"
          >Go to main page</b-button
        >
        <b-form-input
          style="display: inline-block; width: 25%; margin-left: 100px"
          v-model="newContact"
          placeholder="Type to add to contacts"
        ></b-form-input>
        <b-button variant="success" @click="add">Add to contacts</b-button>
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
        style="font-weight: bolder"
      >
        <template #cell(name)="row">
          {{ row.value.first }} {{ row.value.last }}
        </template>

        <template #cell(actions)="row">
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
  </b-container>
</template>
<script>
export default {
  data() {
    return {
      newContact: "",
      contacts: [],
      checkState: false,
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
          label: "Contact",
          sortDirection: "desc",
        },
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
      currentUser:""
    };
  },
  async mounted() {
    await fetch("http://localhost:8085/getContact", {
      method: "get",
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        this.contacts = data;
      });

    for (var i = 0; i < this.contacts.length; i++) {
      this.items.push({ userName: this.contacts[i] });
    }

    // fetch to get the current user
    var obj = {}; 
    await fetch("http://localhost:8085/setting", {
      method: "get",
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        this.currentUser = data[0];
      });
  },
  methods: {
    goBack() {
      console.log();
      this.$router.push({
        name: "signedIn",
      });
    },
    async add() {
      console.log(this.currentUser);
      if (this.currentUser == this.newContact){
        alert("you can't add yourself :D");
        return ;
      }

      for (var i = 0 ; i < this.items.length;i++){
        if (this.items[0].userName == this.newContact){
          alert("This contact is already in you contacts list :D ");
          return ;
        }
      }
      // fetch check first
      await fetch("http://localhost:8085/" + this.newContact, {
        method: "get",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.checkState = data;
        });

      if (this.checkState == false) {
        alert("this user doesn't exist in the system");
        return;
      }

      // fetch
      console.log(this.newContact);
      await fetch("http://localhost:8085/addContact/", {
        method: "post",
        body: JSON.stringify({
          userName: this.newContact,
        }),
      });
      this.items.push({ userName: this.newContact });
      this.newContact = "";
    },
    showMsgBoxTwo(index) {
      this.boxTwo = "";
      this.$bvModal
        .msgBoxConfirm("Please confirm that you want to delete this contact.", {
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
      var contact = this.contacts[index];
      console.log(contact);
      await fetch("http://localhost:8085/deleteContact/", {
        method: "delete",
        body: JSON.stringify({
          userName: contact,
        }),
      });
      //fetch
      this.items.splice(index, 1);
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
  },
};
</script>
<style scoped>
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
</style>