package com.cynoteck.petofyvet.params.getPetList.getPetListResponse;

public class GetPetListData {

        private PagingHeader pagingHeader;

        private PetList[] petList;

        public PagingHeader getPagingHeader ()
        {
            return pagingHeader;
        }

        public void setPagingHeader (PagingHeader pagingHeader)
        {
            this.pagingHeader = pagingHeader;
        }

        public PetList[] getPetList ()
        {
            return petList;
        }

        public void setPetList (PetList[] petList)
        {
            this.petList = petList;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [pagingHeader = "+pagingHeader+", petList = "+petList+"]";
        }
    }
