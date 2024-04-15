import { styled } from 'styled-components';

export const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-items: center;
  flex-direction: column;
`;

export const Inputs = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 10px;
  margin-right: 10px;
`;

export const Form = styled.div`
  display: flex;
  height: 100%;
`;

export const Title = styled.div`
  font-size: 30px;
  font-weight: 700;
  margin-bottom: 30px;
  color: #d1b000;
`;

export const Input = styled.input`
  font-size: 20px;
  height: 30px;
  border-radius: 10px;
  border: none;
  padding: 10px;
  &::placeholder {
    color: darkgray;
    font-size: 20px;
    font-weight: 500;
  }
`;