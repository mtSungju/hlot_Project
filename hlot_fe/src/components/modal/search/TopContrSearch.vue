<template>

  <CompanySearch
    v-if="bCompanySearch"
    @close="bCompanySearch = !bCompanySearch"
    @select="selectCompany"
    style="z-index: 3"
    :compDiv="compDiv"
  />

  <SubModalLayout
    @close="this.$emit('close')"
  >
    <div class="modal-title">
      원계약 검색
    </div>
    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-text-field
        label="원계약명"
        density="compact"
        v-model="searchCondition.topContrNm"
      />
      <v-select
        label="계약구분(품명)"
        density="compact"
        :items="topContrDivs"
        item-title="codeNm"
        item-value="code"
        v-model="searchCondition.topContrDiv"

      />
      <v-text-field
        label="발주처"
        readonly="readonly"
        append-inner-icon="mdi-magnify"
        @click:append-inner="openCompanySearch('COMP03')"
        density="compact"
        v-model="searchCondition.clientCompNm"
      ></v-text-field>
    </div>
    <div class="d-flex flex-wrap ga-3 mt-0">
        <v-text-field
          label="원계약 시작일"
          type="date"
          density="compact"
          v-model="searchCondition.topContrStDate"
        ></v-text-field>
        <v-text-field
          label="원계약 끝일"
          type="date"
          density="compact"
          v-model="searchCondition.topContrEndDate"
        ></v-text-field>
      <v-text-field
        label="수요기관"
        readonly="readonly"
        append-inner-icon="mdi-magnify"
        @click:append-inner="openCompanySearch('COMP02')"
        density="compact"
        v-model="searchCondition.demandInstNm"
      ></v-text-field>
      <v-btn
        color="primary"
        @click="search"
      >
        조회
      </v-btn>
      <v-btn
        color="primary"
        @click="searchCondition = {}"
      >
        초기화
      </v-btn>
    </div>
    <div>
      <table class="custom-table_mt">
        <thead>
        <tr>
          <th>원계약명</th>
          <th>계약구분</th>
          <th>발주처</th>
          <th>수요기관</th>
          <th>계약일자</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="c in topContrs" @click="select(c.topContrId, c.topContrNm)">
          <td>{{ c.topContrNm }}</td>
          <td>{{ c.topContrDiv }}</td>
          <td>{{ c.clientCompNm }}</td>
          <td>{{ c.demandInstNm }}</td>
          <td>{{ c.topContrDate }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </SubModalLayout>
</template>
<script setup>
import SubModalLayout from "@/layouts/SubModalLayout.vue";
import CompanySearch from "@/components/modal/search/CompanySearch.vue";

</script>
<script>
import projectApi from "@/api/project";
import commonApi from "@/api/common"

export default {
  async beforeMount() {
    this.topContrDivs = await commonApi.cmmCodeComp('TCTR');
  },
  data() {
    return {
      bCompanySearch: false,

      compDiv: '',

      topContrDivs: [],
      topContrs: [],

      searchCondition:{
        topContrNm: '',
        topContrDiv: '',
        clientCompId: '',
        demandInstId: '',
        clientCompNm : '',
        demandInstNm : '',
        topContrStDate: '',
        topContrEndDate: '',
      }
    }
  },
  methods : {
    openCompanySearch(compDiv) {
      this.compDiv = compDiv; // 업체구분
      this.bCompanySearch = !this.bCompanySearch;
    },
    selectCompany(obj){ // compDiv [ 업체:COMP01 | 수요기관:COMP02 | 발주처: COMP03 ]
      if(this.compDiv === 'COMP02') { // 수요기관
        this.searchCondition.demandInstId = obj.id;
        this.searchCondition.demandInstNm = obj.nm;
      } else if(this.compDiv === 'COMP03') { // 발주처
        this.searchCondition.clientCompId = obj.id;
        this.searchCondition.clientCompNm = obj.nm;
      }
    },

    /* 원계약 목록조회 */
    async search() {
      this.topContrs = await projectApi.projects(this.searchCondition)
    },
    select(id, nm){
      this.$emit('select', {id, nm});
      this.$emit('close');
    }
  }
}
</script>

<style scoped>
@import "@/assets/styles/common.css";
@import "@/assets/styles/customTable.css";
</style>
